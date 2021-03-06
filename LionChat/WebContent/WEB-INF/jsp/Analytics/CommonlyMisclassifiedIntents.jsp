<div id='chartContainer'></div>
<script type="text/javascript">
    $(function () {
        CanvasJS.addColorSet("blues",
            [//colorSet Array
                "#000088",
                "#0e0e5d",
                "#77aaff",
                "#44aaff",
                "#034c80",
                "#25629b",
                "#a6ced9"
            ].map((a) => ({sort: Math.random(), value: a}))
                .sort((a, b) => a.sort - b.sort)
                .map((a) => a.value));

        var chart = new CanvasJS.Chart("chartContainer", {
            colorSet: [
                "blues"
            ],
            animationEnabled: true,
            title: {
                text: "Frequently Misclassified Questions By Intent"
            },
            axisY:{
                minimum: 0,
                suffix: "   1\u2B50 Ratings"
			},
			axisX:{
				title: "Misclassified Intent of Users Question"
			},
            data: [{
                type: "column",
                dataPoints: ${dataPoints}
            }]
        });
        chart.options.data[0].dataPoints.sort((dataPoint1, dataPoint2) => {return dataPoint2.y-dataPoint1.y});
        chart.render();
    });
</script>