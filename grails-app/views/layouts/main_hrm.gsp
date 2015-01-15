%{--<%@ page import="commons.DateUtils" %>--}%

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Bayalpatra HRM</title>
    <script type="text/javascript" src="${resource(dir:'js',file:'jquery-1.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js',file:'menu-collapsed.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js',file:'jquery.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js',file:'example.js')}"></script>

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'logo_bp.gif')}" type="image/x-icon"/>
    <link rel="stylesheet"  type="text/css"  href="${resource(dir:'css',file:'style.css')}"  media="screen" />
    <link rel="stylesheet"  type="text/css"  href="${resource(dir:'css',file:'main.css')}"  media="screen" />
    <link rel="stylesheet"  type="text/css"  href="${resource(dir:'css',file:'print.css')}" media="print" />
    <g:javascript library="jquery" plugin="jquery" />
    <script type="text/javascript">
        $(document).ready(function(){
            $("#spinner").bind("ajaxSend", function() {
                $(this).show();
            }).bind("ajaxStop", function() {
                        var difference = $(window).height() - $('body').height();
                        if (difference > 0) $('#footer').css('margin-top', difference);
                        $(this).hide();
                    }).bind("ajaxError", function() {
                        $(this).hide();
                    });
            var difference = $(window).height() - $('body').height();
            if (difference > 0) $('#footer').css('margin-top', difference);
        });
    </script>
    <g:layoutHead />
</head>
<body>

<div id="wrapper">
    <div class="header">
        <div class="top_part">
            <div class="logo">
                <h1>
                    <g:link controller='dashboard' action="index"><img src="${createLinkTo(dir:'images', file:'logo.png')}" alt="logo" title="Bayalpatra" /></g:link>
                </h1>
            </div>
            <div class="header_right">
                <span class="admin_welcome1">
                    <sec:ifLoggedIn>
                       %{-- <g:link controller='dashboard' action="index">Dashboard </g:link>|&nbsp;--}%
                        <g:link controller='logout'>Sign Out </g:link>
                        |&nbsp;<g:link controller='user' action="edit" params="[module:'HR']">My Profile </g:link>
                    </sec:ifLoggedIn>

                </span>
                %{--params="[id:employeeInstance.id]"--}%

                <br><br>


                <sec:ifLoggedIn><span class="admin_welcome">&nbsp;&nbsp;Welcome&nbsp;:&nbsp;&nbsp;${session["employeeName"]}</span></sec:ifLoggedIn> &nbsp;&nbsp;&nbsp;


            </div>
            <div class="clear"></div>

        </div>


    </div>
    <div id="spinner" class="spinner" style="display: none;">
        <img id="img-spinner" src="${createLinkTo(dir:'images', file:'spinner.gif')}" alt="Loading"/>
    </div>

    <g:layoutBody />
    <div class="push">
    </div>
</div>
<!-- End Wrapper -->
<div class="clear"></div>
<!-- Footer -->
<div class="footer">
    %{--<% def date = DateUtils.getCurrentDate() %>--}%
    <div class="footer_content">Copyright &copy;%{-- <g:formatDate date="${date}" format="yyyy" />--}% Pfunk, Tattwa. All rights reserved.</div>
</div>
<!-- End Footer -->
%{--<iframe width="1" height="1" id="printFrame" />--}%
</body>
</html>