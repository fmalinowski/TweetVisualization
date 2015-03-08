$(document).ready(function() {

	$(".js-search-form").submit(function(){
		var hashtagValue = $(".js-hashtag").val();

		$.ajax({
			url: "request",
			type: "GET",
			data: {
				hashtag: hashtagValue
			}
		}).done(function(data) {
			$(".js-hashtag").blur();
			$(".top-header").show();
			$(".vertical-container").addClass("vertical-container--fixed-top");
			$(".results").show();
			displayGraph(data)
		});

		return false;
	});
	
});