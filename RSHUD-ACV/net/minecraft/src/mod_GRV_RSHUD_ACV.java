package net.minecraft.src;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class mod_GRV_RSHUD_ACV extends BaseMod {
	
	@MLProp(info="Normal Color.")
	public static String Color_Normal = "b199ffb2";
	@MLProp(info="Warning Message Color.")
	public static String Color_Warning = "b1ffff3f";
	@MLProp(info="Alert Message Color.")
	public static String Color_Alert = "b1ff3f3f";
	@MLProp(info="Marker Color.")
	public static String Color_Marker = "e6667fff";
	@MLProp(info="Marker Color.")
	public static float LowGain = 0.3F;
	@MLProp(info="Marker Color.")
	public static float TextSize = 0.5F;
	@MLProp(info="RECON's ItemID.(ShiftIndex = ItemID - 256, 0 is not use.)", max = 31999)
	public static int itemIDRECON = 22250;
	@MLProp(info="Show Status Massage")
	public static boolean Show_Status = true;


	public static GRV_GuiRSHUD_ACV rshud;
	public static Item itemRECON;
	public static int uniqueRECON;
	public static Class classRECON;



	@Override
	public String getVersion() {
		return "1.4.7-1";
	}

	@Override
	public String getName() {
		return "RSHUD-ACV";
	}

	@Override
	public String getPriorities() {
		return "required-after:mod_GRH_RSHUD";
	}

	@Override
	public void load() {
		try {
			rshud = new GRV_GuiRSHUD_ACV(this);
			GRH_Data.addHUD(rshud);
			
			// 初期値の読み込み
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
			ModLoader.registerKey(this, new KeyBinding(s, 49), false);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("RouteView").toString()
					);
			
			// RECON
			if (itemIDRECON > 0) {
				itemRECON = (new GRV_ItemRECON(itemIDRECON - 256)).setIconCoord(14, 0).setItemName("recon");
				ModLoader.addName(itemRECON, "RECON");
//		        ModLoader.addName(itemRECON, "ja_JP", "探査機");
				ModLoader.addRecipe(new ItemStack(itemRECON, 8), new Object[] {
					"E", 
					"R", 
					"I", 
					Character.valueOf('E'), Item.spiderEye,
					Character.valueOf('I'), Item.ingotIron,
					Character.valueOf('R'), Item.redstone
				});
				uniqueRECON = ModLoader.getUniqueEntityId();
				classRECON = MMM_Helper.getEntityClass(this, "GRV_EntityRECON");
				if (classRECON != null) {
					ModLoader.registerEntityID(classRECON, "RECON", uniqueRECON);
					ModLoader.addEntityTracker(this, classRECON, uniqueRECON, 80, 10, true);
				}
			}
			
		}
		catch (NoClassDefFoundError e) {
			System.out.print("not Found RSHUD.");
		}
	}

	@Override
	public void keyboardEvent(KeyBinding keybinding) {
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

	@Override
	public void addRenderer(Map map) {
		map.put(GRV_EntityMARKER.class, new GRV_RenderMARKER());
		map.put(GRV_EntityRECON.class, new RenderSnowball(Item.snowball.getIconFromDamage(0)));
	}

	@Override
	public Entity spawnEntity(int var1, World var2, double var3, double var5, double var7) {
		// Modloader下では独自に生成するので要らない。
		// というかModLoader環境ではIDが3000以上になるのでここは呼ばれない。
		if (!MMM_Helper.isForge) return null;
		try {
			Constructor<GRV_EntityRECON> lconstructor = classRECON.getConstructor(World.class);
			GRV_EntityRECON lentity = lconstructor.newInstance(var2);
			lentity.entityId = var1;
			lentity.setPosition(var3, var5, var7);
			return lentity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Packet23VehicleSpawn getSpawnPacket(Entity var1, int var2) {
		EntityLiving lthrower = ((GRV_EntityRECON)var1).getThrower();
		return new GRV_PacketRECONSpawn(var1, 0, lthrower == null ? 0 : lthrower.entityId);
	}

}
