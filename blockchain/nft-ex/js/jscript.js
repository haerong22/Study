$(document).ready(function () {
    // page 이동
    $("#page_mynft_detail").on("click", function () {
      window.location.href = "./mynft_detail.html";
    });
  
    $("#page_mynft").on("click", function () {
      window.location.href = "./mynft.html";
    });
});

function onlyNumber(){
  if((event.keyCode > 48 && event.keyCode < 57 ) 
     || event.keyCode == 8 
     || event.keyCode == 37 || event.keyCode == 39 
     || event.keyCode == 46 
     || event.keyCode == 39){
  }else{
   event.returnValue=false;
  }
}