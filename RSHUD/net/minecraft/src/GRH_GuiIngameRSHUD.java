package net.minecraft.src;


public class GRH_GuiIngameRSHUD extends GuiIngame {

	private Minecraft mc;

	public GRH_GuiIngameRSHUD(Minecraft minecraft) {
		super(minecraft);
		mc = minecraft;
	}

	@Override
	public void renderGameOverlay(float f, boolean flag, int i, int j) {
		super.renderGameOverlay(f, flag, i, j);
		ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int k = scaledresolution.getScaledWidth();
		int l = scaledresolution.getScaledHeight();
		ItemStack itemstack = mc.thePlayer.inventory.armorItemInSlot(3);
//        if(mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.itemID == Block.pumpkin.blockID)
		{
			GRH_Data.selectHUD.renderRSHUD(mc, k, l);
		}
	}

}
