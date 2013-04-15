package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;

public class GRH_Data {

	public static GRH_GuiRSHUDConfigure selectHUD = null;
	public static Map<String, GRH_GuiRSHUDConfigure> HUDList = new TreeMap<String, GRH_GuiRSHUDConfigure>();

	public static void addHUD(GRH_GuiRSHUDConfigure guirshud) {
		HUDList.put(guirshud.getHUDName(), guirshud);
		if (mod_GRH_RSHUD.HUDName.equals(guirshud.getHUDName())) {
			selectHUD = guirshud;
		}
	}

	public static void setupProperties(BaseMod basemod)
			throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException {
//    	System.out.println("write property.");
		mod_GRH_RSHUD.HUDName = selectHUD.getHUDName();
		// ModLoaderから丸パクリ
		Logger logger = ModLoader.getLogger();
		Class class1 = basemod.getClass();
		Properties properties = new Properties();
		File cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
		File file = new File(cfgdir, (new StringBuilder(String.valueOf(class1.getSimpleName()))).append(".cfg").toString());
		if(file.exists() && file.canRead()) {
			properties.load(new FileInputStream(file));
		}
		StringBuilder stringbuilder = new StringBuilder();
		Field afield[];
		int j = (afield = class1.getFields()).length;
		for (int i = 0; i < j; i++) {
			Field field = afield[i];
			if((field.getModifiers() & 8) == 0 || 
					!field.isAnnotationPresent(net.minecraft.src.MLProp.class)) {
				continue;
			}
			Class class2 = field.getType();
			MLProp mlprop = (MLProp)field.getAnnotation(net.minecraft.src.MLProp.class);
			String s = mlprop.name().length() != 0 ? mlprop.name() : field.getName();
			Object obj = field.get(null);
			StringBuilder stringbuilder1 = new StringBuilder();
			if (mlprop.min() != (-1.0D / 0.0D)) {
				stringbuilder1.append(String.format(",>=%.1f", new Object[] {
						Double.valueOf(mlprop.min())
				}));
			}
			if (mlprop.max() != (1.0D / 0.0D)) {
				stringbuilder1.append(String.format(",<=%.1f", new Object[] {
						Double.valueOf(mlprop.max())
				}));
			}
			StringBuilder stringbuilder2 = new StringBuilder();
			if (mlprop.info().length() > 0) {
				stringbuilder2.append(" -- ");
				stringbuilder2.append(mlprop.info());
			}
			stringbuilder.append(String.format("%s (%s:%s%s)%s\n", new Object[] {
					s, class2.getName(), obj, stringbuilder1, stringbuilder2
			}));
			logger.finer((new StringBuilder(String.valueOf(s))).append(" set to ").append(obj).toString());
			properties.setProperty(s, obj.toString());
		}
		
		if (!properties.isEmpty() && (file.exists() || file.createNewFile()) && file.canWrite()) {
			properties.store(new FileOutputStream(file), stringbuilder.toString());
		}
	}

	public static GRH_GuiRSHUDConfigure getNextHUD(String name) {
		boolean flag = false;
		for (Map.Entry<String, GRH_GuiRSHUDConfigure> et : HUDList.entrySet()) {
			if (flag) return et.getValue();
			flag = name.equals(et.getKey());
		}
		for (Map.Entry<String, GRH_GuiRSHUDConfigure> et : HUDList.entrySet()) {
			return et.getValue();
		}
		return null;
	}

	public static void initTypeA() {
		GRH_GuiRSHUDConfigure rshud;
		GRH_Data.addHUD(new GRH_GuiRSHUDConfigure(mod_GRH_RSHUD.instance));
		GRH_Data.addHUD(rshud = new GRH_GuiRSHUD_TypeA(mod_GRH_RSHUD.instance));
		
		// 色の生成、普通にやると32Bitの変換ができないので小細工
		mod_GRH_RSHUD.Color_Normal = "00000000".concat(mod_GRH_RSHUD.Color_Normal);
		mod_GRH_RSHUD.Color_Normal = mod_GRH_RSHUD.Color_Normal.substring(mod_GRH_RSHUD.Color_Normal.length() - 8);
		mod_GRH_RSHUD.Color_Warning = "00000000".concat(mod_GRH_RSHUD.Color_Warning);
		mod_GRH_RSHUD.Color_Warning = mod_GRH_RSHUD.Color_Warning.substring(mod_GRH_RSHUD.Color_Warning.length() - 8);
		mod_GRH_RSHUD.Color_Alert = "00000000".concat(mod_GRH_RSHUD.Color_Alert);
		mod_GRH_RSHUD.Color_Alert = mod_GRH_RSHUD.Color_Alert.substring(mod_GRH_RSHUD.Color_Alert.length() - 8);
		rshud.ColorInt_Normal	= (Integer.parseInt(mod_GRH_RSHUD.Color_Normal.substring(0, 4), 16) << 16) | Integer.parseInt(mod_GRH_RSHUD.Color_Normal.substring(4, 8), 16);
		rshud.ColorInt_Warning	= (Integer.parseInt(mod_GRH_RSHUD.Color_Warning.substring(0, 4), 16) << 16) | Integer.parseInt(mod_GRH_RSHUD.Color_Warning.substring(4, 8), 16);
		rshud.ColorInt_Alert	= (Integer.parseInt(mod_GRH_RSHUD.Color_Alert.substring(0, 4), 16) << 16) | Integer.parseInt(mod_GRH_RSHUD.Color_Alert.substring(4, 8), 16);
		rshud.LineR = (float)((rshud.ColorInt_Normal >> 16) & 0xff) / 255F;
		rshud.LineG = (float)((rshud.ColorInt_Normal >> 8) & 0xff) / 255F;
		rshud.LineB = (float)(rshud.ColorInt_Normal & 0xff) / 255F;
	}

	public static void replaceInGameGui(Minecraft minecraft) {
		// アイテムレンダーをオーバーライド
		minecraft.ingameGUI = new GRH_GuiIngameRSHUD(minecraft);
	}

}
