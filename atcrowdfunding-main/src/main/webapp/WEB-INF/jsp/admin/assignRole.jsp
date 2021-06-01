<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<%@ include file="/WEB-INF/jsp/common/css.jsp" %>
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>

    <div class="container-fluid">
      <div class="row">
        <jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">分配角色</li>
				</ol>
			<div class="panel panel-default">
			  <div class="panel-body">
				<form role="form" class="form-inline">
				  <div class="form-group">
					<label for="exampleInputPassword1">未分配角色列表</label><br>
					<select  id="leftSelect" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
						<c:forEach items="${unAssignList }" var="role">
                        	<option value="${role.id }">${role.name }</option>
                        </c:forEach>
                    </select>
				  </div>
				  <div class="form-group" >
                        <ul>
                            <li id="leftToRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                            <br>
                            <li id="rightToLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                        </ul>
				  </div>
				  <div class="form-group" style="margin-left:40px;">
					<label for="exampleInputPassword1">已分配角色列表</label><br>
					<select  id="rightSelect" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
                        <c:forEach items="${assignList }" var="role">
                        	<option value="${role.id }">${role.name }</option>
                        </c:forEach>
                    </select>
				  </div>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
	
    
    <%@ include file="/WEB-INF/jsp/common/js.jsp" %>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
            });
            
            //分配角色
            $("#leftToRightBtn").click(function(){
            	
            	var optionsList=$("#leftSelect option:selected");
            	
            	
            	if(optionsList.length==0){
            		layer.msg("请选择角色进行分配",{icon:5,time:2000});
            		return false;
            	}
            	
            	var str='';
            	$.each(optionsList,function(i,e){
            		var roleId=e.value;
            		str+="roleId="+roleId+"&";
            	});
            	
            	str+="adminId=${param.id}";
            	
            	$.ajax({
            		type:"post",
            		url:"${PATH}/admin/doAssign",
            		data:str,
            		success:function(result){
            			if("ok"==result){
            				layer.msg("分配成功",{icon:6,time:1000},function(){
            					$("#rightSelect").append(optionsList.clone());
            	            	optionsList.remove();
            				});
            				
            			}
            			else{
            				layer.msg("分配失败",{icon:5,time:1000});
            			}
            		}
            	});
            });
            
            //取消分配角色
//             $("#rightToLeftBtn").click(function(){
//             	var optionsList=$("#rightSelect option:selected");
//             	$("#leftSelect").append(optionsList.clone());
//             	optionsList.remove();
//             });
        </script>
  </body>
</html>
    