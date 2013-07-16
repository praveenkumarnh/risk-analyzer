<!DOCTYPE html>
<!--
	Copyright 2010-2012 SciRisk, Inc. All rights reserved.
-->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="Author" content="Daniel Pacak, pacak.daniel@gmail.com" />
		<title>Risk Analyzer Frontend - ${project.version}</title>
		<link rel="stylesheet" type="text/css" href="./extjs/resources/css/ext-all.css">
		<link rel="stylesheet" type="text/css" href="./css/risk-analyzer.css">
		<script type="text/javascript" src="./extjs/bootstrap.js"></script>
		<script type="text/javascript" src="./risk-analyzer/RiskAnalyzer.js"></script>
		<script type="text/javascript" src="./risk-analyzer/MainPanel.js"></script>
		<script type="text/javascript" src="./risk-analyzer/NetworkPanel.js"></script>
		<script src="http://maps.google.com/maps/api/js?sensor=false&amp;v=3" type="text/javascript"></script>
		<script type="text/javascript" src="./risk-analyzer/MapPanel.js"></script>
		<script type="text/javascript" src="./risk-analyzer/ImportWindow.js"></script>
		<script type="text/javascript" src="./risk-analyzer/NodeWindow.js"></script>
		<script type="text/javascript" src="./risk-analyzer/EdgeWindow.js"></script>
		<script type="text/javascript" src="./risk-analyzer/ParamsWindow.js"></script>
		<script type="text/javascript" src="./risk-analyzer/ReportWindow.js"></script>
		<script type="text/javascript" src="./risk-analyzer/HelpWindow.js"></script>
		<script type="text/javascript" src="./risk-analyzer/EmailWindow.js"></script>
		<script type="text/javascript" src="./risk-analyzer/AddressWindow.js"></script>
		<script type="text/javascript" src="./com/danielpacak/risk/analyzer/frontend/FacilityWindow.js"></script>
		<script type="text/javascript">
			Ext.Loader.setConfig({enabled: true});
			Ext.Loader.setPath('Ext.ux', './ux');
			Ext.require([
				'Ext.grid.*',
				'Ext.data.*',
				'Ext.util.*',
				'Ext.Action',
				'Ext.tab.*',
				'Ext.button.*',
				'Ext.form.*',
				'Ext.layout.container.Card',
				'Ext.layout.container.Border',
				'Ext.ux.PreviewPlugin',
				'Ext.ux.statusbar.StatusBar'
			]);
			Ext.onReady(function() {
				new RiskAnalyzer.App();
			});
		</script>
	</head>
	<body>
	</body>
</html>
