package net.minecraft.src;

public class mod_RSHUD_TypeB extends BaseMod {

	@MLProp(info="Line Color (AARRGGBB)")
	public static String Color_Normal = "cc0030ff";
	@MLProp(info="Warning Color(AARRGGBB)")
	public static String Color_Warning = "e5ffff00";
	@MLProp(info="Alert Color(AARRGGBB)")
	public static String Color_Alert = "e5ff0000";
	@MLProp(info="Number Color(AARRGGBB)")
	public static String Color_Number = "cc0090ff";
	
	@MLProp(info="Iron Color (RRGGBB):Default 807050")
	public static String Color_Iron = "807050";
	@MLProp(info="Gold Color(RRGGBB):Default 999000")
	public static String Color_Gold = "999000";
	@MLProp(info="Diamond Color(RRGGBB):Default 009999")
	public static String Color_Diamond = "009999";
	@MLProp(info="Lava Color(RRGGBB):Default ff3000")
	public static String Color_Lava = "ff3000";
	
	@MLProp(info="Line Alpha Value.", min=0.0F, max=1.0F)
	public static float LineAlpha = 0.8F;
	@MLProp(info="Line Width.", min=0.5F)
	public static float LineWidth = 1.0F;
	@MLProp(info="Degree Offset.", min=0, max=36)
	public static int DegOffset = 18;



	@Override
	public String getVersion() {
		return "1.5.1-2";
	}

	@Override
	public String getName() {
		return "RSHUD-TypeB";
	}

	@Override
	public String getPriorities() {
		return "required-after:mod_GRH_RSHUD";
	}

	@Override
	public void load() {
		try {
			GuiRSHUD_TypeB guirshud_typeb = new GuiRSHUD_TypeB(this);
			GRH_Data.addHUD(guirshud_typeb);
			Color_Normal = "00000000".concat(Color_Normal);
			Color_Normal = Color_Normal.substring(Color_Normal.length() - 8);
			Color_Warning = "00000000".concat(Color_Warning);
			Color_Warning = Color_Warning.substring(Color_Warning.length() - 8);
			Color_Alert = "00000000".concat(Color_Alert);
			Color_Alert = Color_Alert.substring(Color_Alert.length() - 8);
			Color_Number = "00000000".concat(Color_Number);
			Color_Number = Color_Number.substring(Color_Number.length() - 8);
			Color_Iron = "000000".concat(Color_Iron);
			Color_Iron = Color_Iron.substring(Color_Iron.length() - 6);
			Color_Gold = "000000".concat(Color_Gold);
			Color_Gold = Color_Gold.substring(Color_Gold.length() - 6);
			Color_Diamond = "000000".concat(Color_Diamond);
			Color_Diamond = Color_Diamond.substring(Color_Diamond.length() - 6);
			Color_Lava = "000000".concat(Color_Lava);
			Color_Lava = Color_Lava.substring(Color_Lava.length() - 6);
			guirshud_typeb.ColorInt_Normal = Integer.parseInt(
					Color_Normal.substring(0, 4), 16) << 16
					| Integer.parseInt(Color_Normal.substring(4, 8), 16);
			guirshud_typeb.ColorInt_Warning = Integer.parseInt(
					Color_Warning.substring(0, 4), 16) << 16
					| Integer.parseInt(Color_Warning.substring(4, 8), 16);
			guirshud_typeb.ColorInt_Alert = Integer.parseInt(
					Color_Alert.substring(0, 4), 16) << 16
					| Integer.parseInt(Color_Alert.substring(4, 8), 16);
			guirshud_typeb.ColorInt_Number = Integer.parseInt(
					Color_Number.substring(0, 4), 16) << 16
					| Integer.parseInt(Color_Number.substring(4, 8), 16);
			guirshud_typeb.ColorInt_Iron = Integer.parseInt(
					Color_Iron.substring(0, 6), 16);
			guirshud_typeb.ColorInt_Gold = Integer.parseInt(
					Color_Gold.substring(0, 6), 16);
			guirshud_typeb.ColorInt_Diamond = Integer.parseInt(
					Color_Diamond.substring(0, 6), 16);
			guirshud_typeb.ColorInt_Lava = Integer.parseInt(
					Color_Lava.substring(0, 6), 16);
			guirshud_typeb.LineR = (float) (guirshud_typeb.ColorInt_Normal >> 16 & 0xff) / 255F;
			guirshud_typeb.LineG = (float) (guirshud_typeb.ColorInt_Normal >> 8 & 0xff) / 255F;
			guirshud_typeb.LineB = (float) (guirshud_typeb.ColorInt_Normal & 0xff) / 255F;
		} catch (NoClassDefFoundError noclassdeffounderror) {
		}
	}
}
