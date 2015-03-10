$(document).ready(function() {

	$(".js-search-form").submit(function(){
		var hashtagValue = $(".js-hashtag").val();
		$(".spinner").addClass("spinner--active");

		$.ajax({
			url: "request",
			type: "GET",
			data: {
				hashtag: hashtagValue
			}
		}).done(function(data) {
			$(".js-hashtag").blur();
			$(".top-header").addClass("top-header--active");
			$(".vertical-container").addClass("vertical-container--fixed-top");

			$(".results").addClass("results--active");
			$(".spinner").removeClass("spinner--active");
			displayGraph(data)
		});

		return false;
	});
	
});