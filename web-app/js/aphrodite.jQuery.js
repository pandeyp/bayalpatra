(function($){
    $.fn.aphrodite = function(existingList,unitList){
        var element = this;
        var caption = $(element).attr('cap');
        var replaceElement = '<input type="button" class="investigation_row" id="button'+caption+'" value="'+caption+'">&nbsp;&nbsp;&nbsp;';
        var identifier = new Array();
        var name = new Array();
        var items = new Array();
        var parents= new Array();
        var unitAmounts = new Array();
        var selectedParent = 'All';
        var root = new Array();
        var firstTime = 0;
        var parentList = '<div id="'+caption+'"><div id="parentList'+caption+'"><table><tr>';
        var opts = $(element)[0].options;
        identifier = $.map(opts, function( elem ) {
            return (elem.value);
        });
        name = $.map(opts, function( elem ) {
            return (elem.text);
        });
        var count=0;
        $('option', element).each(function() {
            var parent='';
            parent=this.getAttribute('rel');
            root.push(parent);
            if(jQuery.inArray(parent,parents)<0){
                parents.push(parent);
                if(parent.length>0){
                    if(count%3==0){
                        parentList=parentList+'<tr><td><span class="fClick'+caption+'" rel="'+parent+'">'+parent+'</span></td>';
                    }else if(count%3==1){
                        parentList=parentList+'<td><span class="fClick'+caption+'" rel="'+parent+'">'+parent+'</span></td>';
                    }else if(count%3==2){
                        parentList=parentList+'<td><span class="fClick'+caption+'" rel="'+parent+'">'+parent+'</span></td></tr>';
                    }
                }
                count++;
            }
        });
        parentList=parentList+'<tr><td colspan="3"><span class="fClick'+caption+'" rel="All">'+'All'+'</span></td></tr>';
        parentList=parentList+'</table></div>';
        for (var i = 0; i < name.length; i++) {
            items.push({id: identifier[i], displayText: name[i],rootParent:root[i]});
        }

        var displayContent = parentList+'<table><tr><td>Search : </td><td><input type="text" style="width: 150px" id="search'+caption+'"/></td><td>Selected : </td><td><select style="width:150px" id="selected'+caption+'"><option value="">-- Select To Remove --</option></select></td></tr></table>';
        $(element).replaceWith(replaceElement);

        $("#button"+caption).click(function(){
            $(".chk").remove();
            $("#selected"+caption).remove();
            if(firstTime<1){
                displayContent = displayContent+"<table id='searchResults"+caption+"'>";
                var limit = 30;
                if(identifier.length<30){
                    limit = identifier.length;
                }
                for(var i=0;i<limit;i++){
                    if(i%2==0){
                        if(unitList){
                            displayContent = displayContent + '<tr><td width="300px"><input type="checkbox" class="chk" id="chkBox'+identifier[i]+'" rel="'+identifier[i]+'"/>&nbsp;'+name[i]+'</td><td><input type="text" style="width:30px;" itemId="'+identifier[i]+'" class="unit" id="chkUnit'+identifier[i]+'"/></td>';
                        }else{
                            displayContent = displayContent + '<tr><td width="300px"><input type="checkbox" class="chk" id="chkBox'+identifier[i]+'" rel="'+identifier[i]+'"/>&nbsp;'+name[i]+'</td>';
                        }
                    }else{
                        if(unitList){
                            displayContent = displayContent + '<td width="300px"><input type="checkbox" class="chk" id="chkBox'+identifier[i]+'" rel="'+identifier[i]+'"/>&nbsp;'+name[i]+'</td></td><td><input type="text" style="width:30px;" itemId="'+identifier[i]+'" class="unit" id="chkUnit'+identifier[i]+'"/></td></tr>';
                        }else{
                            displayContent = displayContent + '<td width="300px"><input type="checkbox" class="chk" id="chkBox'+identifier[i]+'" rel="'+identifier[i]+'"/>&nbsp;'+name[i]+'</td></tr>';
                        }
                    }
                }
                displayContent = displayContent+"</table></div>";
                firstTime=1;
            }
            $(displayContent).dialog({
                modal:true,
                scrollable:true,
                title:caption+" - All",
                width:'600px',
                'resizable':false,
                open:function(){
                    bindList();
                    $(".fClick"+caption).button();
                },
                close:function(){
                    $("#searchResults"+caption).remove();
                }
            });

            $(".unit").keyup(function(){
                if(validateNumeric($(this).val())){
                    saveUnitAmount($(this).attr('itemId'));
                }else{
                    $(this).val('');
                }
            });

            $(".chk").click(function(){
                var alreadyChecked = document.getElementById(existingList).value.split(",");
                if($(this).attr('checked')){
                    alreadyChecked.push($(this).attr('rel'));
                    addToSelect($(this).attr('rel'),searchInListById($(this).attr('rel')));
                }else{
                    alreadyChecked.splice(alreadyChecked.indexOf($(this).attr('rel')),1);
                    removeFromSelect($(this).attr('rel'));
                }
                document.getElementById(existingList).value=alreadyChecked;
                saveUnitAmount($("#chkUnit"+$(this).attr('rel')).attr('itemId'));
            });

            $("#selected"+caption).change(function(){
                $("#chkBox"+$(this).val()).attr('checked',false);
                removeFromSelect($(this).val());
            });

            $('.fClick'+caption).click(function(){
                $("#search"+caption).val('');
                selectedParent=$(this).attr('rel');
                $("span.ui-dialog-title").text(caption+" - "+selectedParent);
                displaySpecificChildOnly(selectedParent);
                event.stopPropagation();
            });

        });

        $(document).delegate("#search"+caption,"keyup",function(){
            var searchStr = $(this).val();
            searchString($.trim(searchStr),selectedParent);
        });

        function displaySpecificChildOnly(parentName){
            var wantedList = new Array();
            if(parentName=='All'){
                for(var j=0;j<items.length;j++){
                    if(wantedList.length<=30){
                        wantedList.push(items[j]);
                    }
                }
            }else{
                for(var i=0;i<items.length;i++){
                    var thisParent = items[i].rootParent;
                    if(thisParent.indexOf(parentName)>-1){
                        if(wantedList.length<=30){
                            wantedList.push(items[i]);
                        }
                    }
                }
            }
            renderDisplay(wantedList);
        }

        function searchInListById(itemId){
            var name = '';
            for(var i=0;i<items.length;i++){
                if(items[i].id==itemId){
                    name = items[i].displayText;
                }
            }
            return name;
        }

        function addToSelect(itemId,itemName){
            if(itemId && itemName){
                var selectedDropDown = $("#selected"+caption)[0].options;
                var existingId = new Array();
                existingId = $.map(selectedDropDown, function( elem ) {
                    return (elem.value);
                });
                if($.inArray(itemId,existingId)==-1){
                    $("#selected"+caption).append('<option value="'+itemId+'">'+itemName+'</option>');
                }
            }
        }

        function saveUnitAmount(itemId){
            var toPush = itemId+":"+$("#chkUnit"+itemId).val();
            var alreadyPresent = -1;
            for(var i=0;i<unitAmounts.length;i++){
                var item = unitAmounts[i].split(":")[0];
                if(item==itemId){
                    alreadyPresent = i;
                }
            }
            if(alreadyPresent>-1){
                unitAmounts.splice(alreadyPresent, 1 );
            }
            unitAmounts.push(toPush);
            flushUnitAmounts();
        }

        function flushUnitAmounts(){
            if(unitList){
                var unitListToFlush = "";
                var alreadyChecked = document.getElementById(existingList).value.split(",");
                $("#"+unitList).val('');
                for(var i=0;i<unitAmounts.length;i++){
                    var itemId = unitAmounts[i].split(":")[0];
                    if($.inArray(itemId,alreadyChecked)>-1){
                        unitListToFlush = unitListToFlush + unitAmounts[i]+",";
                    }
                }
                $("#"+unitList).val(unitListToFlush);
            }
        }

        function validateNumeric(content){
            var min=0;
            var result = true;
            if(isNaN(content)){
                result = false;
            }else{
                if(content<=min){
                    result =  false;
                }
            }
            return result
        }

        function removeFromSelect(itemId){
            var allOption = [];
            $("#selected"+caption+" option").each(function(){
                var eachId = $(this).val();
                var eachText = $(this).html();
                if(eachId!=itemId){
                    var eachLine = '<option value="'+eachId+'">'+eachText+'</option>';
                    allOption.push(eachLine);
                }
            });
            $("#selected"+caption).empty();
            for(var i=0;i<allOption.length;i++){
                $("#selected"+caption).append(allOption[i]);
            }
            var alreadyChecked = document.getElementById(existingList).value.split(",");
            alreadyChecked.splice(alreadyChecked.indexOf(itemId),1);
            document.getElementById(existingList).value=alreadyChecked;
            for(var i=0;i<unitAmounts.length;i++){
                if(itemId==unitAmounts[i].split(":")[0]){
                    unitAmounts.splice(i,1);
                }
            }
            flushUnitAmounts();
        }

        function bindList(){
            var alreadySelected = document.getElementById(existingList).value.split(",");
            $(".chk").each(function(){
                if($.inArray($(this).attr('rel'),alreadySelected)>-1){
                    $(this).attr('checked','true');
                    addToSelect($(this).attr('rel'),searchInListById($(this).attr('rel')));
                }
            });
        }

        function searchString(searchStr,parentName){
            var fetchedList = [];
            if(parentName!='All'){
                if(searchStr.length>1){
                    for(var i=0;i<items.length;i++){
                        var thisName = items[i].displayText.toLowerCase();
                        if(thisName.indexOf(searchStr.toLowerCase())>-1 && items[i].rootParent==selectedParent){
                            if(fetchedList.length<=30){
                                fetchedList.push(items[i]);
                            }
                        }
                    }
                }else{
                    for(var i=0;i<name.length;i++){
                        if(i<=30 && items[i].rootParent==selectedParent){
                            fetchedList.push(items[i]);
                        }
                    }
                }
            }else{
                if(searchStr.length>1){
                    for(var i=0;i<items.length;i++){
                        var thisName = items[i].displayText.toLowerCase();
                        if(thisName.indexOf(searchStr.toLowerCase())>-1){
                            if(fetchedList.length<=30){
                                fetchedList.push(items[i]);
                            }
                        }
                    }
                }else{
                    for(var i=0;i<name.length;i++){
                        if(i<=30){
                            fetchedList.push(items[i]);
                        }
                    }
                }
            }
            renderDisplay(fetchedList);
        }

        function getUnitAmount(itemId){
            var unitAmount="";
            for(var i=0;i<unitAmounts.length;i++){
                var eachAmount = unitAmounts[i].split(":");
                if(eachAmount[0]==itemId){
                    unitAmount = eachAmount[1];
                }
            }
            return unitAmount
        }


        function renderDisplay(fetchedList){
            var newContent = '<table id="searchResults'+caption+'">';
            for(var i=0; i<fetchedList.length;i++){
                var eachUnitAmount=getUnitAmount(fetchedList[i].id);
                if(i%2==0){
                    if(unitList){
                        newContent = newContent + '<tr><td width="300px"><input type="checkbox" class="chk" id="chkBox'+fetchedList[i].id+'" rel="'+fetchedList[i].id+'"/>&nbsp;'+fetchedList[i].displayText+'</td><td><input type="text" style="width:30px;" value="'+eachUnitAmount+'" itemId="'+fetchedList[i].id+'" class="unit" id="chkUnit'+fetchedList[i].id+'"/></td>';
                    }else{
                        newContent = newContent + '<tr><td width="300px"><input type="checkbox" class="chk" id="chkBox'+fetchedList[i].id+'" rel="'+fetchedList[i].id+'"/>&nbsp;'+fetchedList[i].displayText+'</td>';
                    }
                }else{
                    if(unitList){
                        newContent = newContent + '<td width="300px"><input type="checkbox" class="chk" id="chkBox'+fetchedList[i].id+'" rel="'+fetchedList[i].id+'"/>&nbsp;'+fetchedList[i].displayText+'</td><td><input type="text" style="width:30px;" value="'+eachUnitAmount+'" itemId="'+fetchedList[i].id+'" class="unit" id="chkUnit'+fetchedList[i].id+'"/></td></tr>';
                    }else{
                        newContent = newContent + '<td width="300px"><input type="checkbox" class="chk" id="chkBox'+fetchedList[i].id+'" rel="'+fetchedList[i].id+'"/>&nbsp;'+fetchedList[i].displayText+'</td></tr>';
                    }
                }
            }
            newContent = newContent+"</table>"
            $("#searchResults"+caption).replaceWith(newContent);
            bindList();

            $(".unit").keyup(function(){
                if(validateNumeric($(this).val())){
                    saveUnitAmount($(this).attr('itemId'));
                }else{
                    $(this).val('');
                }
            });

            $(".chk").click(function(){
                var alreadyChecked = document.getElementById(existingList).value.split(",");
                if($(this).attr('checked')){
                    alreadyChecked.push($(this).attr('rel'));
                    addToSelect($(this).attr('rel'),searchInListById($(this).attr('rel')));
                }else{
                    alreadyChecked.splice(alreadyChecked.indexOf($(this).attr('rel')),1);
                    removeFromSelect($(this).attr('rel'));
                }
                document.getElementById(existingList).value=alreadyChecked;
                saveUnitAmount($("#chkUnit"+$(this).attr('rel')).attr('itemId'));
            });
        }
    };
})(jQuery);