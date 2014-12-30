<%@ page import="com.bayalpatra.hrm.Department" %>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="main_hrm" />
      <g:set var="entityName" value="${message(code: 'departments.label', default: 'Departments')}" />
      <title><g:message code="default.create.label" args="[entityName]" /></title>

      <g:javascript library="jquery" plugin="jquery"/>
      <jsTree:resources />
      <script type="text/javascript" class="source below">

            $(document).ready(function(){
                  $("#structure").jstree({
                        "plugins" : ["themes","html_data","ui"],
                        "core" : { "initially_open" : [ "li1 " ] }
                  });
            }
                    );

           /* function showValueTextbox(id,val)
            {
                  location.href='${createLink(controller:"departments",action:"show")}/'+id;
            }*/
      </script>
</head>
<body>
<div class="body">

      <p align="right" class="printthis">
            <a href="#">
                  <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
            </a>
      </p>

      <h4>Structure
            <div class="icon">
                  <a href="#"> <img align="right" src="images/print_icon.png" alt=""></a>
                  <a href="#"> <img align="right" src="images/excel_icon.png" alt="" style="padding: 0px 10px 0pt 0pt;"></a>
                  <div style="clear: both;"></div>
            </div></h4>
      <div id="structure">${tree}</div>   </div>
</body>
</html>