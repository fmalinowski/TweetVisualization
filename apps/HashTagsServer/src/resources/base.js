$(document).ready(function() {

	$(".js-search-form").submit(function(){
		var hashtagValue = $(".js-hashtag").val();
		$(".spinner").addClass("spinner--active");

		// To avoid having the lagging when we speak while we are animating
		window.speechSynthesis.getVoices();

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
			displayGraph(data);

			speakAnswer($(".js-hashtag").val());
		});

		return false;
	});

	function speakAnswer(hashtag) {
		var speechObject;

		speechObject = new SpeechSynthesisUtterance(getVoiceSentence(hashtag));
		window.speechSynthesis.speak(speechObject);
	}

	function getVoiceSentence(hashtag) {
		var randomInteger, sentences, sentence, speechObject ;

		sentences = ["He are the twitter connections for ", "Check out the results for ", 
			"Did you know that the hashtag graph looks like this for the hashtag "];
		randomInteger = Math.ceil(Math.random() *100) % 3;

		sentence = sentences[randomInteger] + hashtag;
		return sentence;
	}
	
});