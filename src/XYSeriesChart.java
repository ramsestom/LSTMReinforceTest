import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import commons.FakeHistoryInfoProvider;
import commons.FakeHistoryInfoProviderMaker;
import model.PriceStateObservation;

public class XYSeriesChart extends ApplicationFrame {

/**
 * A demonstration application showing an XY series containing a null value.
 *
 * @param title  the frame title.
 */
public XYSeriesChart(final String title) {

    super(title);
    final XYSeries series = new XYSeries("prices");
    FakeHistoryInfoProviderMaker infosprovider = new FakeHistoryInfoProviderMaker();
	FakeHistoryInfoProvider ip = infosprovider.makeNewInfoProvider();
	for (int i=0; i<1000; i++) {
		 PriceStateObservation infos = ip.step();
		 series.add(i, infos.getClose_price());
	}
    
	final XYSeriesCollection data = new XYSeriesCollection(series);
    final JFreeChart chart = ChartFactory.createXYLineChart(
        "Price chart",
        "Price", 
        "Steps", 
        data,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );

    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    setContentPane(chartPanel);

}



/**
 * Starting point for the demonstration application.
 *
 * @param args  ignored.
 */
public static void main(final String[] args) {

    final XYSeriesChart demo = new XYSeriesChart("Generated price/step");
    demo.pack();
    demo.setVisible(true);

}

}