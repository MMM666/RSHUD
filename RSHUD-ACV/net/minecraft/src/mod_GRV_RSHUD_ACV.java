package net.minecraft.src;

import java.lang.reflect.Constructor;
import java.util.Map;

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
	@MLProp
	public static boolean isInternalServer = true;


	public static Item itemRECON;
	public static int uniqueRECON;
	public static Class classRECON;



	@Override
	public String getName() {
		return "RSHUD-ACV";
	}

	@Override
	public String getPriorities() {
		return MMM_Helper.isClient ? "required-after:mod_GRH_RSHUD" : "";
	}

	@Override
	public String getVersion() {
		return "1.6.1-1";
	}

	@Override
	public void load() {
		// MMMLibのRevisionチェック
		MMM_Helper.checkRevision("1");
		
		if (MMM_Helper.isClient) {
			GRV_Client.load(this);
		}
		
		// RECON
		if (itemIDRECON > 0) {
			itemRECON = (new GRV_ItemRECON(itemIDRECON - 256)).setUnlocalizedName("recon").func_111206_d("recon");
			ModLoader.addName(itemRECON, "RECON");
//	        ModLoader.addName(itemRECON, "ja_JP", "探査機");
			ModLoader.addRecipe(new ItemStack(itemRECON, 8), new Object[] {
				"E", 
				"R", 
				"I", 
				Character.valueOf('E'), Item.spiderEye,
				Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone
			});
//			uniqueRECON = MMM_Helper.getNextEntityID(false);
			classRECON = MMM_Helper.getForgeClass(this, "GRV_EntityRECON");
			if (classRECON != null) {
				MMM_Helper.registerEntity(classRECON, "RECON", 0, this, 80, 10, false);
//				ModLoader.registerEntityID(classRECON, "RECON", uniqueRECON);
//				ModLoader.addEntityTracker(this, classRECON, uniqueRECON, 80, 10, true);
			}
		}
	}

	@Override
	public void keyboardEvent(KeyBinding keybinding) {
		GRV_Client.keyboardEvent(keybinding);
	}

	@Override
	public void addRenderer(Map map) {
		GRV_Client.addRenderer(map);
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
		EntityLivingBase lthrower = ((GRV_EntityRECON)var1).getThrower();
		return new GRV_PacketRECONSpawn(var1, 0, lthrower == null ? 0 : lthrower.entityId);
	}

}
