/*
김현아_화면구현.js
*/

$(document).ready(function() {
	$(".slideshow").each(function() {
		var $container = $(this), 
			$slideGroup = $container.find(".slideshow-slides"), 
			$slides = $slideGroup.find(".slide"),		// 슬라이드 이미지
			$nav = $container.find(".slideshow-nav"),	// 다음, 이전 버튼
			$indicator = $container.find(".slideshow-indicator"),	// 바로가기 버튼들을 의미하는 객체
			slideCount = $slides.length, 
			indicatorHTML = "", 
			currentIndex = 0, 
			duration = 500,		// 슬라이드 애니메이션 소요 시간
			easing = "easeInOutExpo", 
			interval = 3000, 
			timer;

		$slides.each(function(i) {
			$(this).css({left:100 * i + "%"});
			indicatorHTML += "<a href='#'>" + (i + 1) + "</a>";
		});
		$indicator.html(indicatorHTML);

		function goToSlide(idx) {	// 원하는 슬라이드를 보여주는 함수
			$slideGroup.animate({left:-100 * idx + "%"}, duration, easing);
			currentIndex = idx;
			updateNav();
		}

		function updateNav() {		// 현재 보이는 슬라이드 이미지에 따라 탐색과 표시를 업데이트하는 함수
			var $navPrev = $nav.find(".prev");
			var $navNext = $nav.find(".next");

			if (currentIndex == 0) {
				$navPrev.addClass("disabled");
			} else {	
				$navPrev.removeClass("disabled");
				}

			if (currentIndex == slideCount - 1) {
				$navNext.addClass("disabled");
			} else {	
				$navNext.removeClass("disabled");
			}

			$indicator.find("a").removeClass("active").eq(currentIndex).addClass("active");
		}

		function startTimer() {		// 타이머를 시작하는 함수
			timer = setInterval(function() {
				var nextIndex = (currentIndex + 1) % slideCount;
				goToSlide(nextIndex);
			}, interval);
		}

		function stopTimer() {		// 타이머를 중지시키는 함수
			clearInterval(timer);
		}

		$nav.on("click", "a", function(event) {
			event.preventDefault();

			if ($(this).hasClass("prev")) {
				goToSlide(currentIndex - 1);
			} else {
				goToSlide(currentIndex + 1);
			}
		});

		$indicator.on("click", "a", function(event) {
			event.preventDefault();

			if (!$(this).hasClass("active")) {
				goToSlide($(this).index());
			}
		});

		$container.on({
			mouseenter:stopTimer,	
			mouseleave:startTimer	
		});

		goToSlide(currentIndex);

		startTimer();
	});
});