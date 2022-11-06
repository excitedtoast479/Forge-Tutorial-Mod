package com.excitedtoast479.tutorialmod.screen;

import com.excitedtoast479.tutorialmod.block.ModBlocks;
import com.excitedtoast479.tutorialmod.block.entity.MediumVaseBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class MediumVaseMenu extends AbstractContainerMenu {
    public final MediumVaseBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public MediumVaseMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(20));
    }

    public MediumVaseMenu(int id, Inventory inv, BlockEntity entity,ContainerData data) {
        super(ModMenuTypes.MEDIUM_VASE_MENU.get(), id);
        checkContainerSize(inv, 20);
        blockEntity = (MediumVaseBlockEntity) entity;
        this.level = inv.player.level;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 52, 18-35));
            this.addSlot(new SlotItemHandler(handler, 1, 70, 18-35));
            this.addSlot(new SlotItemHandler(handler, 2, 88, 18-35));
            this.addSlot(new SlotItemHandler(handler, 3, 106, 18-35));
            this.addSlot(new SlotItemHandler(handler, 4, 52, 36-35));
            this.addSlot(new SlotItemHandler(handler, 5, 70, 36-35));
            this.addSlot(new SlotItemHandler(handler, 6, 88, 36-35));
            this.addSlot(new SlotItemHandler(handler, 7, 106, 36-35));
            this.addSlot(new SlotItemHandler(handler, 8, 52, 54-35));
            this.addSlot(new SlotItemHandler(handler, 9, 70, 54-35));
            this.addSlot(new SlotItemHandler(handler, 10, 88, 54-35));
            this.addSlot(new SlotItemHandler(handler, 11, 106, 54-35));
            this.addSlot(new SlotItemHandler(handler, 12, 52, 72-35));
            this.addSlot(new SlotItemHandler(handler, 13, 70, 72-35));
            this.addSlot(new SlotItemHandler(handler, 14, 88, 72-35));
            this.addSlot(new SlotItemHandler(handler, 15, 106, 72-35));
            this.addSlot(new SlotItemHandler(handler, 16, 52, 90-35));
            this.addSlot(new SlotItemHandler(handler, 17, 70, 90-35));
            this.addSlot(new SlotItemHandler(handler, 18, 88, 90-35));
            this.addSlot(new SlotItemHandler(handler, 19, 106, 90-35));
        });

        addDataSlots(data);


    }



    public boolean isCrafting() {
        return data.get(0) > 0;
    }


    //public void setFluid(FluidStack fluidStack) {
    //    this.fluidStack = fluidStack;
    //}

    //public FluidStack getFluidStack() {
    //    return fluidStack;
    //}

    public MediumVaseBlockEntity getBlockEntity() {
        return this.blockEntity;
    }





    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 5;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 4;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 20;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }


    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.MEDIUM_VASE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18 + 23));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144+23));
        }
    }
}
