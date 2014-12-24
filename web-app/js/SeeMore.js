(function($){
    $.fn.seeMore = function(){
        var element = this;
        var thisId = this.attr('id');
        var originalText = this.html();
        if(originalText.length>30){
            var truncatedText=truncateText(originalText);
            this.html(truncatedText);
            this.attr('rel',originalText);
            this.append('<br/><span id="sMore'+thisId+'">See More</span>');
            $("#sMore"+thisId).click(function(){
                toggleText();
            });
            $("#sMore"+thisId).hover(function (){
                    $(this).css("text-decoration", "underline");
                },function(){
                    $(this).css("text-decoration", "none");
                }
            );
        }

        function toggleText(){
            var relText = $(element).attr('rel');
            if(originalText.length<=relText.length){
                $(element).html(originalText);
                $(element).attr('rel',truncatedText);
                $(element).append('<br/><span id="sMore'+thisId+'">See Less</span>');
            }else{
                $(element).html(truncatedText);
                $(element).attr('rel',originalText);
                $(element).append('<br/><span id="sMore'+thisId+'">See More</span>');
            }
            $("#sMore"+thisId).click(function(){
                toggleText();
            });
            $("#sMore"+thisId).hover(function (){
                    $(this).css("text-decoration", "underline");
                },function(){
                    $(this).css("text-decoration", "none");
                }
            );
        }

        function truncateText(originalText){
            var truncatedText = originalText.substring(0,30);
            var counter=29;
            var check=0;
            var testChar = originalText.charAt(counter);
            if(testChar==' '){
                check=1;
            }else{
                check=0;
            }
            while(check>0){
                counter++;
                testChar=originalText.charAt(counter);
                check=0;
            }
            truncatedText=originalText.substring(0,counter-1);
            truncatedText=truncatedText+'...'
            return truncatedText
        }
    };
})(jQuery);