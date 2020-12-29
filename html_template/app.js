function ArticleDetail__Body__init() {
	if ( toastui === undefined ) {
		return;
	}
	
	var body = document.querySelector('.article_body_body');
	var initialValue = body.innerHTML.trim();
	
	var viewer = new toastui.Editor.factory({
		el : body,
		initialValue : initialValue,
		viewer : true,
	    plugins: [toastui.Editor.plugin.codeSyntaxHighlight]
	});
}

ArticleDetail__Body__init();

setInterval(function(){         
            var timer = new Date();
            var h = timer.getHours();
            var m = timer.getMinutes();
            var s = timer.getSeconds();
 document.getElementById('clock').innerHTML = h + ":" + m + ":" + s;},1000);






var slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
  showSlides(slideIndex += n);
}

function currentSlide(n) {
  showSlides(slideIndex = n);
}

function showSlides(n) {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("dot");
  if (n > slides.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";  
  }
  for (i = 0; i < dots.length; i++) {
      dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active";
}

function setBackground(n){
 
  var number = n;
  var imgurl = "downloads/";
  var url = imgurl+number+".jpg";
  document.getElementById("mpart").style.backgroundImage= `url('${url}')`;
  
}
