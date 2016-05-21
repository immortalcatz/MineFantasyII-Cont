package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityBigFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;

public class BigFurnacePacket extends PacketMF
{
	public static final String packetName = "MF2_BigfurnPkt";
	private int[] coords = new int[3];
	private float[] progress = new float[2];
	
	public BigFurnacePacket(TileEntityBigFurnace tile)
	{
		coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
		progress = new float[]{tile.progress, tile.maxProgress};
		if(progress[0] > progress[1])
		{
			progress[0] = progress[1];
		}
	}

	public BigFurnacePacket() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        progress[0] = packet.readFloat();
        progress[1] = packet.readFloat();
        
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        
        if(entity != null && entity instanceof TileEntityBigFurnace)
        {
	        TileEntityBigFurnace tile = (TileEntityBigFurnace)entity;
	        tile.progress = progress[0];
	        tile.maxProgress = progress[1];
        }
	}

	@Override
	public String getChannel()
	{
		return packetName;
	}

	@Override
	public void write(ByteBuf packet) 
	{
		for(int a = 0; a < coords.length; a++)
		{
			packet.writeInt(coords[a]);
		}
		packet.writeFloat(progress[0]);
		packet.writeFloat(progress[1]);
	}
}
