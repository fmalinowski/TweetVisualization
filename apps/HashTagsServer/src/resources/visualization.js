var svg, graphFrame;

function displayGraph(JSONdata) {
	var resultContainerSel;
	var width, height, forceGraph, link, nodesGroup, node, text, zoom;

	width = 800;
	height = 500;

	resultContainerSel = ".results__container";

	forceGraph = d3.layout.force()
					.linkDistance(80)
					.charge(-300)
					.size([width, height]);

	$(".results__container svg").remove();

	zoom = d3.behavior
			.zoom()
			.translate([0, 0])
    		.scale(1)
    		.on("zoom", rescale);

	svg = d3.select(resultContainerSel).append("svg")
			.attr("width", width)
			.attr("height", height)
			.attr("class", "graph-container");

	graphFrame = svg.append("g");

	svg.call(zoom);

	forceGraph.nodes(JSONdata.nodes)
				.links(JSONdata.links)
				.start();

	link = graphFrame
				.selectAll(".link")
				.data(JSONdata.links, function(d) { return d.source.id + "-" + d.target.id; })
				.enter().append("line")
				.classed("link", true);
				// We could have used .style("stroke-width", function(d) { return Math.sqrt(d.value);}); //value of 5 max!
				// to have a stroker link according to the value data attribute

	nodesGroup = graphFrame.selectAll("g")
					.data(JSONdata.nodes, function(d) { return d.id; })
					.enter()
					.append("g");

	node = nodesGroup.append("circle")
				.classed("node", true)
				.attr("r", 5) // We could make a function to get the radius bigger: attr(r, function(d) { return d.value; })
				.on("mouseover", mouseoverNode)
				.on("mouseout", mouseoutNode)
				.call(forceGraph.drag);
				// We could have used .style("fill", function(d) { return color(d.group); }) 
				// to color in different colros using the data attribute group.

	text = nodesGroup.append("text")
					.text(function(d) { return d.name; })
					.classed("node-title", true)
					.attr("x", 8)
    				.attr("y", ".31em");

	forceGraph.on("tick", function() {
		link.attr("x1", function(d) { return d.source.x; })
			.attr("y1", function(d) { return d.source.y; })
			.attr("x2", function(d) { return d.target.x; })
			.attr("y2", function(d) { return d.target.y; });

		nodesGroup.attr("transform", function(d) { return "translate(" + [d.x, d.y] + ")"; });
	});

}

function rescale() {
	var translation, scale;
	
	translation = d3.event.translate;
	scale = d3.event.scale;

	graphFrame.attr("transform", "translate(" + translation + ") scale(" + scale + ")");
}

function mouseoverNode(nodeData) {
	var node, nodeText, groupNode, targetNodes;

	node = d3.select(this);
	nodeText = d3.select(this.parentNode).select("text");
	
	$(".results__info").html("Over: " + nodeData.name);
	node.classed("node--hovered", true);
	nodeText.classed("node-title--node-hovered", true);

	groupNode = this.parentNode;
	groupNode.parentNode.appendChild(groupNode); // This is too put the node on top of the otehr ones so that we can see the label
	
	targetNodes = {};

	d3.selectAll(".link")
	 	.filter(function(d) {
	  		return ((nodeData.index == d.source.index) || (nodeData.index == d.target.index));
	 	})
	 	.each(function(d) {
	 		var targetNodeKey;

	 		d3.select(this).classed("link--node-hovered", true);

	 		// This is to highlight the targeted nodes
	 		targetNodeKey = d.source.index === nodeData.index ? d.target.index : d.source.index;
	 		targetNodes[targetNodeKey] = true;
	 	});

	d3.selectAll(".node")
		.filter(function(d) {
			if (targetNodes[d.index]) {
				return true;
			}
			else {
				return false;
			}
	 	})
	 	.each(function(d) {
	 		var targetNode, nodeText;

	 		targetNode = d3.select(this);
	 		nodeText = d3.select(this.parentNode).select("text");
			
	 		d3.select(this).classed("node--highlight-edge-target", true);
	 		nodeText.classed("node-title--highlight-edge-target", true);
	 	});

}

function mouseoutNode(nodeData) {
	var node, nodeText, links, targetNodes;

	node = d3.select(this);
	nodeText = d3.select(this.parentNode).select("text");

	node.classed("node--hovered", false);
	nodeText.classed("node-title--node-hovered", false);

	targetNodes = {};

	d3.selectAll(".link")
	 	.filter(function(d) {
	  		return ((nodeData.index == d.source.index) || (nodeData.index == d.target.index));
	 	})
	 	.each(function(d) {
	 		d3.select(this).classed("link--node-hovered", false);

	 		// This is to highlight the targeted nodes
	 		targetNodeKey = d.source.index === nodeData.index ? d.target.index : d.source.index;
	 		targetNodes[targetNodeKey] = true;
	 	});

	d3.selectAll(".node")
		.filter(function(d) {
			if (targetNodes[d.index]) {
				return true;
			}
			else {
				return false;
			}
	 	})
	 	.each(function(d) {
	 		var targetNode, nodeText;

	 		targetNode = d3.select(this);
	 		nodeText = d3.select(this.parentNode).select("text");

	 		d3.select(this).classed("node--highlight-edge-target", false);
	 		nodeText.classed("node-title--highlight-edge-target", false);
	 	});
}

$(document).ready(function() {

	$(".js-submit").on("click", function(){
		var hashtagValue = $(".js-hashtag").val();

		$.ajax({
			url: "request",
			type: "GET",
			data: {
				hashtag: hashtagValue
			}
		}).done(function(data) {
			displayGraph(data)
		});
	});
	
});