package uk.co.akm.twistertest.plot.function;

import java.text.NumberFormat;

/**
 * The labels used to describe the function plot.
 *
 * Created by Thanos Mavroidis on 01/08/2017.
 */
public final class FunctionPlotText {
    /**
     * The minimum x-value label.
     */
    public final String xMinStr;

    /**
     * The maximum x-value label.
     */
    public final String xMaxStr;

    /**
     * The minimum y-value label.
     */
    public final String yMinStr;

    /**
     * The maximum y-value label.
     */
    public final String yMaxStr;

    /**
     * The x-axis label.
     */
    public final String xAxisStr;

    /**
     * The y-axis label.
     */
    public final String yAxisStr;

    /**
     * The function plot title.
     */
    public final String title;

    /**
     * Defines the labels used to describe the function plot.
     *
     * @param data the data of the function to be plotted
     * @param xNumberFormat the number format used to print numbers for x-axis values
     * @param yNumberFormat the number format used to print numbers for y-axis values
     * @param xMinFormat the format string used to render the xMin title (please note that the number on this format will be represented as a string, already converted using the @xNumberFormat)
     * @param xMaxFormat the format string used to render the xMax title (please note that the number on this format will be represented as a string, already converted using the @xNumberFormat)
     * @param yMinFormat the format string used to render the yMin title (please note that the number on this format will be represented as a string, already converted using the @yNumberFormat)
     * @param yMaxFormat the format string used to render the yMax title (please note that the number on this format will be represented as a string, already converted using the @yNumberFormat)
     * @param xAxisStr the x-axis label
     * @param yAxisStr the y-axis label
     * @param title the title of the function plot
     */
    public FunctionPlotText(FunctionData data, NumberFormat xNumberFormat, NumberFormat yNumberFormat, String xMinFormat, String xMaxFormat, String yMinFormat, String yMaxFormat, String xAxisStr, String yAxisStr, String title) {
        xMinStr = String.format(xMinFormat, xNumberFormat.format(data.xMin));
        xMaxStr = String.format(xMaxFormat, xNumberFormat.format(data.xMax));
        yMinStr = String.format(yMinFormat, yNumberFormat.format(data.yMin));
        yMaxStr = String.format(yMaxFormat, yNumberFormat.format(data.yMax));
        this.xAxisStr = xAxisStr;
        this.yAxisStr = yAxisStr;
        this.title = title;
    }
}
