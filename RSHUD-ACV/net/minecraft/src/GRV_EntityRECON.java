package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

import javax.transaction.xa.Xid;

import net.minecraft.server.MinecraftServer;

public class GRV_EntityRECON extends EntityThrowable {
	
	// TODO:継承関係が変わったら修正
	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	// 
	public boolean enable;
	public int batteryCount;
	//
	public Entity targetEntity;
	public int index;
	private int countEnemy;
	private int countOther;
	public EntityLivingBase thrower;

	
	
	public GRV_EntityRECON(World par1World) {
		super(par1World);
	}

	public GRV_EntityRECON(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
		thrower = par2EntityLiving;
	}

	public GRV_EntityRECON(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		
		// 初期化
		targetEntity = null;
		enable = false;
		// デフォルトでは３０秒
		batteryCount = 600;
	}

	@Override
	public void setPositionAndRotation2(double par1, double par3, double par5,
			float par7, float par8, int par9) {
		this.setPosition(par1, par3, par5);
		this.setRotation(par7, par8);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);
		
		par1nbtTagCompound.setBoolean("Enable", enable);
		par1nbtTagCompound.setInteger("Battery", batteryCount);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);
		
		enable = par1nbtTagCompound.getBoolean("Enable");
		batteryCount = par1nbtTagCompound.getInteger("Battery");
	}

	@Override
	public boolean canBeCollidedWith() {
		return targetEntity == null;
	}

	@Override
	public boolean canBePushed() {
		return targetEntity == null;
	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		if (movingobjectposition.entityHit != null) {
			if (!movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), 0));
			targetEntity = movingobjectposition.entityHit;
//			setDead();
		} else {
			// 壁に当たった
			xTile = movingobjectposition.blockX;
			yTile = movingobjectposition.blockY;
			zTile = movingobjectposition.blockZ;
			inTile = worldObj.getBlockId(xTile, yTile, zTile);
			motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
			motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
			motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
			float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
			posX -= (motionX / (double)f2) * 0.05000000074505806D;
			posY -= (motionY / (double)f2) * 0.05000000074505806D;
			posZ -= (motionZ / (double)f2) * 0.05000000074505806D;
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			inGround = true;
			setPrivates();
		}
		
		// プレーヤーのレベルに応じて起動個数を決定
		if (thrower instanceof EntityPlayer) {
			int lwakeCount = ((EntityPlayer)thrower).experienceLevel / 10;
			List llist = worldObj.loadedEntityList;
			if (llist != null) {
				for (int li = 0; li < llist.size(); li++) {
					Entity lentity = (Entity)llist.get(li);
					if (lentity instanceof GRV_EntityRECON) {
						GRV_EntityRECON lrecon = (GRV_EntityRECON)lentity;
						if (lrecon.enable && lrecon.index++ > lwakeCount) {
							lrecon.setDead();
						}
					}
				}
			}
			// 起動
			enable = true;
			index = 0;
		}
	}

	@Override
	public void onUpdate() {
		getPrivates();
		if (targetEntity != null) {
			// くっつき虫
			xTile = (int)(posX - 0.5D);
			yTile = (int)posY;
			zTile = (int)(posZ - 0.5D);
			inTile = worldObj.getBlockId(xTile, yTile, zTile);
		}
		setPrivates();
		
		if (targetEntity != null) {
			// くっつき虫
			posX = targetEntity.posX;
			posY = targetEntity.posY;
			posZ = targetEntity.posZ;
		} else {
			super.onUpdate();
		}
		
		if (enable) {
			if (batteryCount-- < 0) {
				// バッテリー切れ
				setDead();
			}
			
			// TODO: 1.3.1の不具合対策、ホントは！が要らない、むしろ逆
			if (worldObj.isRemote) {
				// Client
				// 有効範囲内のMOBを取得
				countOther = 0;
				countEnemy = 0;
				List llist1 = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(8D, 8D, 8D));
				if (llist1 != null) {
					Iterator literator1 = llist1.iterator();
					while (literator1.hasNext()) {
						Entity lentity = (Entity)literator1.next();
						if (lentity instanceof GRV_EntityRECON) continue;
						if (lentity == thrower) continue;
						if (mod_GRV_RSHUD_ACV.isInternalServer && MMM_Helper.mc.isIntegratedServerRunning()) {
							// サーバーのEntity
							WorldServer lws = MinecraftServer.getServer().worldServers[0];
							lentity = lws.getEntityByID(lentity.entityId);
						}
						GRV_GuiRSHUD_ACV.addRECONSensing(lentity);
						if (lentity instanceof IMob) {
							countEnemy++;
						} else {
							countOther++;
						}
					}
				}
			}
		}
		
		if (targetEntity != null) {
			if (targetEntity.isDead) {
				targetEntity = null;
			} else {
				posX = targetEntity.posX;
				posY = targetEntity.posY + targetEntity.getEyeHeight();
				posZ = targetEntity.posZ;
			}
		}
	}

	public boolean getPrivates() {
		try {
			xTile = (Integer)ModLoader.getPrivateValue(EntityThrowable.class, this, 0);
			yTile = (Integer)ModLoader.getPrivateValue(EntityThrowable.class, this, 1);
			zTile = (Integer)ModLoader.getPrivateValue(EntityThrowable.class, this, 2);
			inTile = (Integer)ModLoader.getPrivateValue(EntityThrowable.class, this, 3);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean setPrivates() {
		try {
			ModLoader.setPrivateValue(EntityThrowable.class, this, 0, xTile);
			ModLoader.setPrivateValue(EntityThrowable.class, this, 1, yTile);
			ModLoader.setPrivateValue(EntityThrowable.class, this, 2, zTile);
			ModLoader.setPrivateValue(EntityThrowable.class, this, 3, inTile);
			ModLoader.setPrivateValue(EntityThrowable.class, this, 8, 0);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getCountOther() {
		return countOther > 99 ? 99 : countOther;
	}

	public int getCountEnemy() {
		return countEnemy > 99 ? 99 : countEnemy;
	}

}
