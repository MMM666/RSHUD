package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class mod_GRH_RSHUD extends BaseMod {

	@MLProp(info="Normal Color.")
	public static String Color_Normal = "cc4cff7f";
	@MLProp(info="Warning Message Color.")
	public static String Color_Warning = "e5ffff00";
	@MLProp(info="Alert Message Color.")
	public static String Color_Alert = "e5ff0000";
	@MLProp(info="Line Alpha Value.", min=0.0F, max=1.0F)
	public static float LineAlpha = 0.8F;
	@MLProp(info="Line Width.", min=0.5F)
	public static float LineWidth = 1.0F;
	@MLProp(info="Degree Offset.", min=0, max=36)
	public static int DegOffset = 18;

	@MLProp(info="Weapon & Ammo List(Bow:Arrow[,Arrow;Bow:Arrow,Arrow];)")
	public static String projectionArms = "261:262;";
	@MLProp(info="Replace GuiIngame.")
	public static boolean replaceGuiIngame = false;
	@MLProp(info="GUI Enable(dont't use GUI is false)")		// GUIの有効無効
	public static boolean guiEnable = true;
	@MLProp(info="Default HUD")								// HUDの表示
	public static String HUDName = "TypeA";

	
	public static KeyBinding guiKey;
	public static mod_GRH_RSHUD instance;
	

	
	@Override
	public String getName() {
		return "RSHUD";
	}

	@Override
	public String getPriorities() {
		// 必須
		return "required-after:mod_MMM_MMMLib";
	}
	
	@Override
	public String getVersion() {
		return "1.6.2-1";
	}

	@Override
	public void load() {
		// MMMLibのRevisionチェック
		MMM_Helper.checkRevision("1");
		
		// ゲーム起動後の設定用フック
		if (replaceGuiIngame) {
			ModLoader.setInGUIHook(this, true, false);
		} else {
			ModLoader.setInGameHook(this, true, false);
		}
		instance = this;
		
		// GUI を開くキーの登録と名称変換テーブルの登録
		if (guiEnable) {
			String s = "key.RSHUD";
			guiKey = new KeyBinding(s, 25);
			ModLoader.registerKey(this, guiKey, false);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("RSHUD Gui").toString()
					);
		}
		
		// HUDの登録
		GRH_Data.initTypeA();
	}

	@Override
	public void modsLoaded() {
		// 投射体リストの解析
		String[] w1 = projectionArms.split(";");
		for (int i = 0; i < w1.length; i++) {
			String[] w2 = w1[i].split(":");
			if (w2.length < 2) {
				continue;
			}
			for (int j = 0; j < w2.length; j++) {
				String[] w3 = w2[1].split(",");
				setAmmoIndex(w2[0], w3);
			}
		}
	}

	@Override
	public void keyboardEvent(KeyBinding keybinding) {
		// GUIを開く
		Minecraft mcGame = ModLoader.getMinecraftInstance();
//    	if (ModLoader.isGUIOpen(null)) {
		if (mcGame.theWorld != null && mcGame.currentScreen == null) {
			ModLoader.openGUI(mcGame.thePlayer, GRH_Data.selectHUD);
		}
	}

	@Override
	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen) {
		GRH_Data.replaceInGameGui(minecraft);
		// 一回でいい
		return false;
	}

	@Override
	public boolean onTickInGame(float f, Minecraft minecraft) {
		if (minecraft.currentScreen == null || minecraft.currentScreen instanceof GuiChat) {
			ScaledResolution scaledresolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
			int k = scaledresolution.getScaledWidth();
			int l = scaledresolution.getScaledHeight();
			GRH_Data.selectHUD.renderRSHUD(minecraft, k, l, f);
		}
		return true;
	}



	private boolean setAmmoIndex(String weaponIndex, String[] ammoIndex) {
		try {
			String[] ls = weaponIndex.split("-");
			int projectionWeaponIndex = Integer.valueOf(ls[0]);
			int projectionWeaponDamage = ls.length > 1 ? Integer.valueOf(ls[1]) : 0;
			
			List<ItemStack> weaponAmmos = new ArrayList<ItemStack>();
			for (int j = 0; j < ammoIndex.length; j++) {
				ls = ammoIndex[j].split("-");
				int lindex = Integer.valueOf(ls[0]);
				int ldamage = ls.length > 1 ? Integer.valueOf(ls[1]) : 0;
				weaponAmmos.add(new ItemStack(lindex, 1, ldamage));
			}
			if (weaponAmmos.isEmpty()) {
				return false;
			}
			GRH_GuiRSHUDConfigure.projectorList.put(new ItemStack(projectionWeaponIndex, 1, projectionWeaponDamage), weaponAmmos);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}
