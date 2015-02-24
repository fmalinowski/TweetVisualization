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
			var $resultContainer = $(".results__container");
			$resultContainer.html("Here is the answer from the server:" + data.answer);
		});
	});
	
});