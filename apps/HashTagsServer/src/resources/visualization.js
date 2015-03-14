var svg, graphFrame;

function displayGraph(JSONdata) {
	var resultContainerSel;
	var width, height, forceGraph, zoom, link, nodesGroup;

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

	link = createEdges(JSONdata, graphFrame);

	nodesGroup = createNodes(JSONdata, graphFrame, forceGraph);

	forceGraph.on("tick", function() {
		link.attr("x1", function(d) { return d.source.x; })
			.attr("y1", function(d) { return d.source.y; })
			.attr("x2", function(d) { return d.target.x; })
			.attr("y2", function(d) { return d.target.y; });

		nodesGroup.attr("transform", function(d) { return "translate(" + [d.x, d.y] + ")"; });
	});

}

function createEdges(JSONdata, graphFrame) {
	return graphFrame
		.selectAll(".link")
		.data(JSONdata.links, function(d) { return d.source.id + "-" + d.target.id; })
		.enter().append("line")
		.classed("link", true)
		.style("stroke-width", function(d) { return d.weight; });
		// We could have used .style("stroke-width", function(d) { return Math.sqrt(d.value);}); //value of 5 max!
		// to have a stroker link according to the value data attribute
}

function createNodes(JSONdata, graphFrame, forceGraph) {
	var nodesGroup;

	nodesGroup = graphFrame.selectAll("g")
					.data(JSONdata.nodes, function(d) { return d.id; })
					.enter()
					.append("g");

	// Append the circles
	nodesGroup.append("circle")
		.classed("node", true)
		// .attr("class", function(d) { if(d.hashtagOrMention == 1) {return 'node--mention:';} else {return 'node';}});
		.attr("r", function(d) { return d.radius; }) // We could make a function to get the radius bigger: attr(r, function(d) { return d.value; })
		.on("mouseover", mouseoverNode)
		.on("mouseout", mouseoutNode)
		.call(forceGraph.drag);
		//.attr("r", 5) // We could make a function to get the radius bigger: attr(r, function(d) { return d.value; })
		// We could have used .style("fill", function(d) { return color(d.group); }) 
		// to color in different colros using the data attribute group.

	// Append the labels
	nodesGroup.append("text")
		.text(function(d) { return d.name; })
		.classed("node-title", true)
		.attr("x", 8)
    	.attr("y", ".31em");

    return  nodesGroup;
}

function rescale() {
	var translation, scale;
	
	translation = d3.event.translate;
	scale = d3.event.scale;

	graphFrame.attr("transform", "translate(" + translation + ") scale(" + scale + ")");
}

function mouseoverNode(nodeData) {
	var targetNodes;

	highlightHoveredNode(true, nodeData, this);
	putNodeInForeground(this);
	targetNodes = highlightEdgesAndGetTargetNodes(true, nodeData);
	highlighTargetNodes(true, targetNodes);
}

function mouseoutNode(nodeData) {
	var targetNodes;

	highlightHoveredNode(false, nodeData, this);
	putNodeInForeground(this);
	targetNodes = highlightEdgesAndGetTargetNodes(false, nodeData);
	highlighTargetNodes(false, targetNodes);
}

function highlightHoveredNode(highlightBool, nodeData, node) {
	var displayedText, nodeEl, nodeText;

	nodeEl = d3.select(node);
	nodeText = d3.select(node.parentNode).select("text");

	displayedText = highlightBool ? ("Over: " + nodeData.name) : "";
	$(".results__info").html(displayedText);

	nodeEl.classed("node--hovered", highlightBool);
	nodeText.classed("node-title--node-hovered", highlightBool);
}

function putNodeInForeground(node) {
	var groupNode;

	groupNode = node.parentNode;
	groupNode.parentNode.appendChild(groupNode); // This is too put the node on top of the otehr ones so that we can see the label
}

function highlightEdgesAndGetTargetNodes(highlightBool, nodeData) {
	var targetNodes = {};

	d3.selectAll(".link")
	 	.filter(function(d) {
	  		return ((nodeData.index == d.source.index) || (nodeData.index == d.target.index));
	 	})
	 	.each(function(d) {
	 		var targetNodeKey;

	 		d3.select(this).classed("link--node-hovered", highlightBool);

	 		// This is to highlight the targeted nodes
	 		targetNodeKey = d.source.index === nodeData.index ? d.target.index : d.source.index;
	 		targetNodes[targetNodeKey] = true;
	 	});

	return targetNodes;
}

function highlighTargetNodes(highlightBool, targetNodes) {
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
			
	 		d3.select(this).classed("node--highlight-edge-target", highlightBool);
	 		nodeText.classed("node-title--highlight-edge-target", highlightBool);
	 	});
}
