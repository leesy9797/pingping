/*
������_ȭ�鱸��.js
*/

$(document).ready(function() {
	$(".slideshow").each(function() {
		var $container = $(this), 
			$slideGroup = $container.find(".slideshow-slides"), 
			$slides = $slideGroup.find(".slide"),		// �����̵� �̹���
			$nav = $container.find(".slideshow-nav"),	// ����, ���� ��ư
			$indicator = $container.find(".slideshow-indicator"),	// �ٷΰ��� ��ư���� �ǹ��ϴ� ��ü
			slideCount = $slides.length, 
			indicatorHTML = "", 
			currentIndex = 0, 
			duration = 500,		// �����̵� �ִϸ��̼� �ҿ� �ð�
			easing = "easeInOutExpo", 
			interval = 3000, 
			timer;

		$slides.each(function(i) {
			$(this).css({left:100 * i + "%"});
			indicatorHTML += "<a href='#'>" + (i + 1) + "</a>";
		});
		$indicator.html(indicatorHTML);

		function goToSlide(idx) {	// ���ϴ� �����̵带 �����ִ� �Լ�
			$slideGroup.animate({left:-100 * idx + "%"}, duration, easing);
			currentIndex = idx;
			updateNav();
		}

		function updateNav() {		// ���� ���̴� �����̵� �̹����� ���� Ž���� ǥ�ø� ������Ʈ�ϴ� �Լ�
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

		function startTimer() {		// Ÿ�̸Ӹ� �����ϴ� �Լ�
			timer = setInterval(function() {
				var nextIndex = (currentIndex + 1) % slideCount;
				goToSlide(nextIndex);
			}, interval);
		}

		function stopTimer() {		// Ÿ�̸Ӹ� ������Ű�� �Լ�
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