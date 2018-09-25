package com.fafasoft.flow.ui.substance;

import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceColorSchemeBundle;
import org.jvnet.substance.api.SubstanceConstants.ColorShiftKind;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.colorscheme.BaseLightColorScheme;
import org.jvnet.substance.colorscheme.EbonyColorScheme;
import org.jvnet.substance.colorscheme.TintColorScheme;
import org.jvnet.substance.colorscheme.ToneColorScheme;
import org.jvnet.substance.painter.border.ClassicInnerBorderPainter;
import org.jvnet.substance.painter.decoration.ArcDecorationPainter;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import org.jvnet.substance.painter.gradient.GlassGradientPainter;
import org.jvnet.substance.painter.highlight.GlassHighlightPainter;
import org.jvnet.substance.shaper.ClassicButtonShaper;
import org.jvnet.substance.watermark.SubstanceCrosshatchWatermark;

import java.awt.*;

public class MySubstanceSkin1 extends SubstanceSkin {
	public static final String NAME = "Raven Graphite";

	public MySubstanceSkin1() {
		SubstanceColorScheme activeScheme = new EbonyColorScheme() {
			// 深色。
			public Color getDarkColor() {
				//353535  424242
				return Color.decode("#51514f");	
			}
		};
		SubstanceColorScheme defaultScheme = new BaseLightColorScheme(
				"Raven Graphite Default") {
			// 超轻颜色
			public Color getUltraLightColor() {//
				return Color.decode("#464646");
			}
			public Color getBackgroundFillColor(){
				return Color.decode("#464646");
			}
			// 超 光
			public Color getExtraLightColor() {
				return Color.decode("#464646");
			}
			// 额外的光色
			public Color getLightColor() {//
				return Color.decode("#464646");
			}
			// 中等颜色。 边框
			public Color getMidColor() {//#232323 #4a4949
				return Color.decode("#4a4949");
			}
			// 深色。
			public Color getDarkColor() {
				//353535  424242
				return Color.decode("#424242");
			}
			// 超深色。
			public Color getUltraDarkColor() {
				return Color.decode("#353535");
			}

			// 前景色
			public Color getForegroundColor() {//#EFEEEE d6d6d4
				return Color.decode("#DCDBDB");
			}
			public Color getLineColor() {//5D5D5D 636262
				 return Color.decode("#636262");
			}
		};
		SubstanceColorScheme disabledScheme = new ToneColorScheme(
				new EbonyColorScheme(), 0.35) {
			@Override
			public Color getForegroundColor() {
				return Color.decode("#202020");
			}
			// 超 光
			public Color getExtraLightColor() {
				return Color.decode("#5f5f5f");
			}
		}.named("Raven Graphite Glass Disabled");

	
		SubstanceColorScheme selectedDisabledScheme = new TintColorScheme(
				new EbonyColorScheme(), 0.2) {
			@Override
			public Color getForegroundColor() {
				return Color.decode("#202020");
			}
		}.named("Raven Graphite Glass Selected Disabled");
	
		SubstanceColorSchemeBundle defaultSchemeBundle = new SubstanceColorSchemeBundle(
				activeScheme, defaultScheme, disabledScheme);
		// register highlight alphas
		SubstanceColorScheme highlightScheme = new EbonyColorScheme();

		defaultSchemeBundle.registerHighlightColorScheme(highlightScheme, 0.4f,
				ComponentState.ROLLOVER_UNSELECTED);
		defaultSchemeBundle.registerHighlightColorScheme(highlightScheme,
				0.55f, ComponentState.SELECTED);
		defaultSchemeBundle.registerHighlightColorScheme(highlightScheme, 0.7f,
				ComponentState.ROLLOVER_SELECTED);
		defaultSchemeBundle.registerHighlightColorScheme(highlightScheme,
				0.65f, ComponentState.ARMED, ComponentState.ROLLOVER_ARMED);

		// register schemes for disabled states
		defaultSchemeBundle.registerColorScheme(disabledScheme, 0.5f,
				ComponentState.DISABLED_UNSELECTED);

		defaultSchemeBundle.registerColorScheme(selectedDisabledScheme, 0.5f,
				ComponentState.DISABLED_SELECTED);

		this.registerDecorationAreaSchemeBundle(defaultSchemeBundle,DecorationAreaType.NONE);

		this.registerAsDecorationArea(defaultScheme,
				DecorationAreaType.PRIMARY_TITLE_PANE,
				DecorationAreaType.SECONDARY_TITLE_PANE,
				DecorationAreaType.HEADER, DecorationAreaType.FOOTER,
				DecorationAreaType.GENERAL, DecorationAreaType.TOOLBAR);

		setSelectedTabFadeStart(0.3);
		setSelectedTabFadeEnd(0.6);

		this.buttonShaper = new ClassicButtonShaper();
		this.watermark = new SubstanceCrosshatchWatermark();
		this.gradientPainter = new GlassGradientPainter();
		this.decorationPainter = new ArcDecorationPainter();
		this.highlightPainter = new GlassHighlightPainter();
		this.borderPainter = new ClassicInnerBorderPainter(0.5f,
				ColorShiftKind.TONE);
	
		
	}

	public String getDisplayName() {
		return NAME;
	}

	
}
