<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<jsp:include page="../fragments/header.jsp" />
<body>
	<div id="wrapper">
		<jsp:include page="../fragments/navigator.jsp" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header"> Portfolio Setting </h1>
				</div>
			</div>
			<div class="row">
				<c:if test="${not empty msg}">
					<div class="alert alert-${css} alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<strong>${msg}</strong>
					</div>
				</c:if>
				<table class="table table-striped">
					<thead>					
						<tr>
							<th>Portfolio ID</th>
							<th>Portfolio Name</th>	
							<th>Description</th>
							<th>Last Updated Date</th>
							<th><div class="form-group">
								<spring:url value="/settings/portfolio/add" var="urlAddPortfolio" />
								<button class="btn btn-success pull-right" onclick="location.href='${urlAddPortfolio}'">Add</button>
								</div>
							</th>
						</tr>
					</thead>
					<c:forEach var="portfolioInfoModel" items="${portfolioInfoModels}">
						<tr>
							<td>${portfolioInfoModel.portfolioId}</td>
							<td>${portfolioInfoModel.portfolioNM}</td>
							<td>${portfolioInfoModel.description}</td>
							<td>${portfolioInfoModel.crtnTime}</td>
							<td>
								<spring:url value="/settings/portfolio/${portfolioInfoModel.portfolioId}/update" var="updateUrl" />
								<spring:url value="/settings/portfolio/${portfolioInfoModel.portfolioId}/delete" var="deleteUrl" />
								<button class="btn btn-primary btn-xs" onclick="location.href='${updateUrl}'">
									UPDATE
								</button>
								<button class="btn btn-danger btn-xs" onclick="this.diabled=true;post('${deleteUrl}')">
									DELETE
								</button>
								
							</td>
						</tr>
					</c:forEach>
					
				</table>
				
				
			</div>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	<jsp:include page="../fragments/footer.jsp" />
	
    <script type="text/javascript">
    function post(path, params, method) {
    	method = method || "post"; 

    	var form = document.createElement("form");
    	form.setAttribute("method", method);
    	form.setAttribute("action", path);

    	for ( var key in params) {
    		if (params.hasOwnProperty(key)) {
    			var hiddenField = document.createElement("input");
    			hiddenField.setAttribute("type", "hidden");
    			hiddenField.setAttribute("name", key);
    			hiddenField.setAttribute("value", params[key]);

    			form.appendChild(hiddenField);
    		}
    	}

    	document.body.appendChild(form);
    	form.submit();
    }
    </script>
    
</body>
</html>