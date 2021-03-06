package net.minecraft.src;


public class GRV_PacketRECONSpawn extends Packet23VehicleSpawn {
	
	public GRV_PacketRECONSpawn(Entity par1Entity, int par2, int par3) {
		super(par1Entity, par2, par3);
	}

	@Override
	public void processPacket(NetHandler par1NetHandler) {
		if (par1NetHandler instanceof NetClientHandler) {
			Minecraft mc = ModLoader.getMinecraftInstance();
			WorldClient lworld = mc.theWorld;
			double lx = (double)this.xPosition / 32.0D;
			double ly = (double)this.yPosition / 32.0D;
			double lz = (double)this.zPosition / 32.0D;
			
			Entity le = (mc.thePlayer.entityId == throwerEntityId) ? mc.thePlayer : lworld.getEntityByID(throwerEntityId);
			if (le instanceof EntityLivingBase) {
				GRV_EntityRECON lentity = new GRV_EntityRECON(lworld, (EntityLivingBase)le);
				
				lentity.setPositionAndRotation(lx, ly, lz, 0F, 0F);
				lentity.setVelocity((double)this.speedX / 8000.0D, (double)this.speedY / 8000.0D, (double)this.speedZ / 8000.0D);
				lentity.serverPosX = this.xPosition;
				lentity.serverPosY = this.yPosition;
				lentity.serverPosZ = this.zPosition;
				lentity.entityId = this.entityId;
				lworld.addEntityToWorld(this.entityId, lentity);
			}
		}
	}
	
}
