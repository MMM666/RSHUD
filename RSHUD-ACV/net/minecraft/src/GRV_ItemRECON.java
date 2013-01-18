﻿package net.minecraft.src;

import java.lang.reflect.Constructor;

import net.minecraft.client.Minecraft;

public class GRV_ItemRECON extends ItemSnowball {

	
	public GRV_ItemRECON(int par1) {
		super(par1);
		setMaxStackSize(64);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		// RECON射出
		if (!par3EntityPlayer.capabilities.isCreativeMode) {
			par1ItemStack.stackSize--;
		}
		
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!par2World.isRemote) {
			try {
				Constructor<GRV_EntityRECON> lconstructor = mod_GRV_RSHUD_ACV.classRECON.getConstructor(World.class, EntityLiving.class);
				par2World.spawnEntityInWorld(lconstructor.newInstance(par2World, par3EntityPlayer));
			} catch (Exception e) {
			}
		}
		
		return par1ItemStack;
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return 0xff888888;
	}

}
