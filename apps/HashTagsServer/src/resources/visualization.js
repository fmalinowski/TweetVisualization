function displayGraph(JSONdata) {
	var resultContainerSel;
	var width, height, forceGraph, svg, link, node;

	width = 800;
	height = 500;

	resultContainerSel = ".results__container";

	forceGraph = d3.layout.force()
					.linkDistance(50)
					.charge(-80)
					.size([width, height]);

	svg = d3.select(resultContainerSel).append("svg")
			.attr("width", width)
			.attr("height", height);

	forceGraph.nodes(JSONdata.nodes)
				.links(JSONdata.links)
				.start();

	link = svg.selectAll(".link")
				.data(JSONdata.links)
					.enter().append("line")
							.attr("class", "link");
							// We could have used .style("stroke-width", function(d) { return Math,.sqrt(d.value);}); 
							// to have a stroker link according to the value data attribute

	node = svg.selectAll(".node")
				.data(JSONdata.nodes)
					.enter().append("circle")
							.attr("class", "node")
							.attr("r", 5) // We could make a function to get the radius bigger: attr(r, function(d) { return d.value; })
							.call(forceGraph.drag);
							// We could have used .style("fill", function(d) { return color(d.group); }) 
							// to color in different colros using the data attribute group.

	node.append("title")
		.text(function(d) { return d.name; });

	forceGraph.on("tick", function() {
		link.attr("x1", function(d) { return d.source.x; })
			.attr("y1", function(d) { return d.source.y; })
			.attr("x2", function(d) { return d.target.x; })
			.attr("y2", function(d) { return d.target.y; });

		node.attr("cx", function(d) { return d.x; })
			.attr("cy", function(d) { return d.y; });
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