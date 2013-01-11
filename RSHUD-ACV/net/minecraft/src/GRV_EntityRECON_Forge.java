package net.minecraft.src;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.*;


public class GRV_EntityRECON_Forge extends GRV_EntityRECON implements IEntityAdditionalSpawnData {

	public GRV_EntityRECON_Forge(World par1World) {
		super(par1World);
	}

	public GRV_EntityRECON_Forge(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public GRV_EntityRECON_Forge(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}


	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(thrower == null ? entityId : thrower.entityId);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		int lthrower = data.readInt();
		if (lthrower != 0) {
			Entity lentity = worldObj.getEntityByID(lthrower);
			if (lentity instanceof EntityLiving) {
				thrower = (EntityLiving)lentity;
			}
		}
	}

}
