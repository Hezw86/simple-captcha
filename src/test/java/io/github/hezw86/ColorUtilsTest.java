package io.github.hezw86;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Color;

public class ColorUtilsTest {
    
    @Test
    public void testCalculateContrastColorWhite() {
        Color background = Color.WHITE;
        Color contrast = ColorUtils.calculateContrastColor(background);
        assertEquals(Color.BLACK, contrast);
    }
    
    @Test
    public void testCalculateContrastColorBlack() {
        Color background = Color.BLACK;
        Color contrast = ColorUtils.calculateContrastColor(background);
        assertEquals(Color.WHITE, contrast);
    }
    
    @Test
    public void testCalculateContrastColorLightGray() {
        Color background = new Color(240, 240, 240);
        Color contrast = ColorUtils.calculateContrastColor(background);
        assertEquals(Color.BLACK, contrast);
    }
    
    @Test
    public void testCalculateContrastColorDarkGray() {
        Color background = new Color(50, 50, 50);
        Color contrast = ColorUtils.calculateContrastColor(background);
        assertEquals(Color.WHITE, contrast);
    }
    
    @Test
    public void testCalculateContrastColorDarkBlue() {
        Color background = new Color(0, 0, 139);
        Color contrast = ColorUtils.calculateContrastColor(background);
        assertEquals(Color.WHITE, contrast);
    }
    
    @Test
    public void testCalculateContrastColorLightBlue() {
        Color background = new Color(173, 216, 230);
        Color contrast = ColorUtils.calculateContrastColor(background);
        assertEquals(Color.BLACK, contrast);
    }
    
    @Test
    public void testCalculateContrastColorWithThreshold() {
        Color background = new Color(128, 128, 128);
        
        Color contrast1 = ColorUtils.calculateContrastColor(background, 0.3);
        assertEquals(Color.WHITE, contrast1);
        
        Color contrast2 = ColorUtils.calculateContrastColor(background, 0.1);
        assertEquals(Color.BLACK, contrast2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateContrastColorNull() {
        ColorUtils.calculateContrastColor(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateContrastColorInvalidThreshold() {
        ColorUtils.calculateContrastColor(Color.WHITE, 1.5);
    }
    
    @Test
    public void testCalculateLuminance() {
        double whiteLuminance = ColorUtils.calculateLuminance(Color.WHITE);
        assertTrue(whiteLuminance > 0.9);
        
        double blackLuminance = ColorUtils.calculateLuminance(Color.BLACK);
        assertTrue(blackLuminance < 0.1);
    }
    
    @Test
    public void testCalculateContrastRatio() {
        double ratio = ColorUtils.calculateContrastRatio(Color.BLACK, Color.WHITE);
        assertTrue(ratio > 20.0);
        
        double ratioSame = ColorUtils.calculateContrastRatio(Color.WHITE, Color.WHITE);
        assertEquals(1.0, ratioSame, 0.01);
    }
    
    @Test
    public void testIsDark() {
        assertTrue(ColorUtils.isDark(Color.BLACK));
        assertTrue(ColorUtils.isDark(new Color(50, 50, 50)));
        assertFalse(ColorUtils.isDark(Color.WHITE));
        assertFalse(ColorUtils.isDark(new Color(200, 200, 200)));
    }
    
    @Test
    public void testIsLight() {
        assertTrue(ColorUtils.isLight(Color.WHITE));
        assertTrue(ColorUtils.isLight(new Color(200, 200, 200)));
        assertFalse(ColorUtils.isLight(Color.BLACK));
        assertFalse(ColorUtils.isLight(new Color(50, 50, 50)));
    }
    
    @Test
    public void testInvert() {
        Color inverted = ColorUtils.invert(Color.WHITE);
        assertEquals(Color.BLACK, inverted);
        
        Color inverted2 = ColorUtils.invert(Color.BLACK);
        assertEquals(Color.WHITE, inverted2);
        
        Color gray = new Color(128, 128, 128);
        Color invertedGray = ColorUtils.invert(gray);
        assertEquals(127, invertedGray.getRed());
        assertEquals(127, invertedGray.getGreen());
        assertEquals(127, invertedGray.getBlue());
    }
    
    @Test
    public void testSelectBestContrast() {
        Color background = new Color(0, 0, 139);
        Color best = ColorUtils.selectBestContrast(
            background,
            Color.BLACK,
            Color.WHITE,
            new Color(255, 255, 0)
        );
        assertEquals(Color.WHITE, best);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSelectBestContrastNullBackground() {
        ColorUtils.selectBestContrast(null, Color.BLACK, Color.WHITE);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSelectBestContrastEmptyCandidates() {
        ColorUtils.selectBestContrast(Color.WHITE);
    }
}