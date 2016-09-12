/**
 * 
 */

function openBackWindow(url,popName){
        var popupWindow = window.open(url,popName,'scrollbars=1,height=650,width=1050,menubar=no');
          if($.browser.msie){
            popupWindow.blur();
            window.focus();
        }else{
           blurPopunder();
        }
      };

    function blurPopunder() {
            var winBlankPopup = window.open("about:blank");
            if (winBlankPopup) {
                winBlankPopup.focus();
                winBlankPopup.close()
            }
    };

