<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/" var="urlHome" />
<spring:url value="/register" var="urlRegistration" />
<spring:url value="/tables/price" var="urlPriceTable" />
<spring:url value="/tables/delta" var="urlDeltaTable" />
<spring:url value="/tables/detail" var="urlDetailTable" />
<spring:url value="/priceChart" var="urlPriceChart" />
<spring:url value="/deltaChart" var="urlDeltaChart" />
<spring:url value="/deltaChart2" var="urlDeltaChart2" />
<spring:url value="/settings/process" var="urlProcessSetting" />
<spring:url value="/settings/scenario" var="urlScenarioSetting" />
<spring:url value="/settings/portfolio" var="urlPortfolioSetting" />
<spring:url value="/pricer" var="urlPricer" />
<spring:url value="/process/price" var="urlProcessPrice" />
<spring:url value="/process/delta" var="urlProcessDelta" />
<spring:url value="/process/portfolio" var="urlProcessPortfolio" />

<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${urlHome}">AAD Manger</a>
    </div>
    <!-- /.navbar-header -->
    
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">                
                <li>
                    <a href="${urlRegistration}"><i class="fa fa-dashboard fa-fw"></i> Product Registration</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-table fa-fw"></i> Result Tables <span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${urlPriceTable}">Price Table</a>
                        </li>
                        <li>
                            <a href="${urlDeltaTable}">Delta Table</a>
                        </li>
                        <li>
                            <a href="${urlDetailTable}">Detail Table</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Result Charts <span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${urlPriceChart}">Price Charts</a>
                        </li>
                        <li>
                            <a href="${urlDeltaChart}">Delta Charts</a>
                        </li>
                        <li>
                            <a href="${urlDeltaChart2}">Delta Charts2</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="${urlPricer}"><i class="fa fa-desktop fa-fw"></i> Pricer </a>
                </li>
                <li>
                	<a href="#"><i class="fa fa-tasks fa-fw"></i> Process <span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${urlProcessPrice}">Price Process</a>
                        </li>
                        <li>
                            <a href="${urlProcessDelta}">Delta Process</a>
                        </li>                        
                        <li>
                            <a href="${urlProcessPortfolio}">Portfolio Process</a>
                        </li>
                    </ul>
                </li>
                <li>
                	<a href="#"><i class="fa fa-asterisk fa-fw"></i> Settings <span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${urlProcessSetting}">Process Setting</a>
                        </li>
                        <li>
                            <a href="${urlScenarioSetting}">Scenario Setting</a>
                        </li>
                        <li>
                            <a href="${urlPortfolioSetting}">Portfolio Setting</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>
