function displayGraph(JSONdata) {
	var resultContainerSel;
	var width, height, forceGraph, svg, link, nodesGroup, node, text;

	width = 800;
	height = 500;

	resultContainerSel = ".results__container";

	forceGraph = d3.layout.force()
					.linkDistance(80)
					.charge(-300)
					.size([width, height]);

	$(".results__container svg").remove();

	svg = d3.select(resultContainerSel).append("svg")
			.attr("width", width)
			.attr("height", height);

	forceGraph.nodes(JSONdata.nodes)
				.links(JSONdata.links)
				.start();

	link = svg.selectAll(".link")
				.data(JSONdata.links, function(d) { return d.source.id + "-" + d.target.id; })
					.enter().append("line")
							.attr("class", "link");
							// We could have used .style("stroke-width", function(d) { return Math,.sqrt(d.value);}); 
							// to have a stroker link according to the value data attribute

	nodesGroup = svg.selectAll("g")
				.data(JSONdata.nodes, function(d) { return d.id; })
				.enter()
				.append("g");

	node = nodesGroup.append("circle")
					 .attr("class", "node")
					 .attr("r", 5) // We could make a function to get the radius bigger: attr(r, function(d) { return d.value; })
					 .call(forceGraph.drag);
					 // We could have used .style("fill", function(d) { return color(d.group); }) 
					 // to color in different colros using the data attribute group.

	text = nodesGroup.append("text")
					 .text(function(d) { return d.name; })
					 .attr("class", "title")
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