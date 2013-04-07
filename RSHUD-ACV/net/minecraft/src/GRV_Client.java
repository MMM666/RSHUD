package net.minecraft.src;

import static net.minecraft.src.mod_GRV_RSHUD_ACV.*;
import java.util.Map;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

public class GRV_Client {

	public static GRV_GuiRSHUD_ACV rshud;



	public static void load(BaseMod pMod) {
		if (MMM_Helper.isClient) {
			try {
				rshud = new GRV_GuiRSHUD_ACV(pMod);
				GRH_Data.addHUD(rshud);
				
				// èâä˙ílÇÃì«Ç›çûÇ›
				Color_Normal = "00000000".concat(Color_Normal);
				Color_Normal = Color_Normal.substring(Color_Normal.length() - 8);
				Color_Warning = "00000000".concat(Color_Warning);
				Color_Warning = Color_Warning.substring(Color_Warning.length() - 8);
				Color_Alert = "00000000".concat(Color_Alert);
				Color_Alert = Color_Alert.substring(Color_Alert.length() - 8);
				Color_Marker = "00000000".concat(Color_Marker);
				Color_Marker = Color_Marker.substring(Color_Marker.length() - 8);
				rshud.ColorInt_Normal = Integer.parseInt(Color_Normal.substring(0, 4), 16) << 16 | Integer.parseInt(Color_Normal.substring(4, 8), 16);
				rshud.ColorInt_Warning = Integer.parseInt(Color_Warning.substring(0, 4), 16) << 16 | Integer.parseInt(Color_Warning.substring(4, 8), 16);
				rshud.ColorInt_Alert = Integer.parseInt(Color_Alert.substring(0, 4), 16) << 16 | Integer.parseInt(Color_Alert.substring(4, 8), 16);
				rshud.ColorInt_Maker = Integer.parseInt(Color_Marker.substring(0, 4), 16) << 16 | Integer.parseInt(Color_Marker.substring(4, 8), 16);
				rshud.lowGain = LowGain;
				rshud.textSize = TextSize;
				rshud.isStatus = Show_Status;
				
				String s = "key.RSHUD.ACV.Route";
				ModLoader.registerKey(pMod, new KeyBinding(s, 49), false);
				ModLoader.addLocalization(
						(new StringBuilder()).append(s).toString(),
						(new StringBuilder()).append("RouteView").toString()
						);
				
			}
			catch (NoClassDefFoundError e) {
				System.out.print("not Found RSHUD.");
			}
		}
		
		// RECON
		if (itemIDRECON > 0) {
			itemRECON = (new GRV_ItemRECON(itemIDRECON - 256)).setUnlocalizedName("recon");
			ModLoader.addName(itemRECON, "RECON");
//	        ModLoader.addName(itemRECON, "ja_JP", "íTç∏ã@");
			ModLoader.addRecipe(new ItemStack(itemRECON, 8), new Object[] {
				"E", 
				"R", 
				"I", 
				Character.valueOf('E'), Item.spiderEye,
				Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone
			});
			uniqueRECON = MMM_Helper.getNextEntityID(false);
			classRECON = MMM_Helper.getForgeClass(pMod, "GRV_EntityRECON");
			if (classRECON != null) {
				ModLoader.registerEntityID(classRECON, "RECON", uniqueRECON);
				ModLoader.addEntityTracker(pMod, classRECON, uniqueRECON, 80, 10, true);
			}
		}
	}

	public static void keyboardEvent(KeyBinding keybinding) {
		Minecraft mcGame = ModLoader.getMinecraftInstance();
		if (mcGame.theWorld != null && mcGame.currentScreen == null && GRH_Data.selectHUD == rshud) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				rshud.setStartRoute(mcGame.thePlayer);
				System.out.println("Route Reset.");
			} else {
				rshud.toggleRouteView(mcGame.thePlayer);
				System.out.println("Route.");
			}
		}
	}

	public static void addRenderer(Map map) {
		map.put(GRV_EntityMARKER.class, new GRV_RenderMARKER());
		map.put(GRV_EntityRECON.class, new RenderSnowball(mod_GRV_RSHUD_ACV.itemRECON));
	}

}
