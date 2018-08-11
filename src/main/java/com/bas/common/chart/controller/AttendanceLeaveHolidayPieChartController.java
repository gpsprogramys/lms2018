package com.bas.common.chart.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("request")
public class AttendanceLeaveHolidayPieChartController {
	
	/*
	 * A simple renderer for setting custom colors for a pie chart.
	 */
	public static class PieRenderer {
		private Color[] color;

		public PieRenderer(Color[] color) {
			this.color = color;
		}

		public void setColor(PiePlot plot, DefaultPieDataset dataset) {
			List<Comparable> keys = dataset.getKeys();
			int aInt;

			for (int i = 0; i < keys.size(); i++) {
				aInt = i % this.color.length;
				plot.setSectionPaint(keys.get(i), this.color[aInt]);
			}
		}
	}
	
    @RequestMapping("/showAttendanceLeaveHolidayPieChart")
	public void showAttendanceLeaveHolidayPieChart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");
		ServletOutputStream os = response.getOutputStream();

		final DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("ETS", new Double(43.2));
		data.setValue("Java", new Double(10.0));
		data.setValue("PHP", new Double(27.5));
/*		data.setValue("Four", new Double(17.5));
		data.setValue("Five", new Double(11.0));
		data.setValue("Six", new Double(19.4));
*/		JFreeChart chart = ChartFactory.createPieChart("", data, true, true, false);
		Font font = new Font("vardana", Font.BOLD, 14);
		TextTitle title = chart.getTitle();
		title.setFont(font);
		
		PiePlot plot = (PiePlot)chart.getPlot(); 
		plot.setOutlineVisible(false);
		plot.setBackgroundPaint(Color.white);
		Font font1 = new Font("", Font.PLAIN, 10);
		plot.setLabelFont(font1);
        // Specify the colors here 
        Color[] colors = {Color.blue, Color.yellow, Color.green}; 
        PieRenderer renderer = new PieRenderer(colors); 
        renderer.setColor(plot, data); 
		RenderedImage chartImage = chart.createBufferedImage(200, 200);

		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();
	}

}
