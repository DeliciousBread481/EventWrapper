package io.github.lounode.eventwrapper.event.entity;


import io.github.lounode.eventwrapper.eventbus.api.Cancelable;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.Entity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class EntityEventWrapper extends EventWrapper {
    private final Entity entity;

    public EntityEventWrapper(Entity entity)
    {
        this.entity = entity;
    }

    public EntityEventWrapper(EntityEvent event) {
        this(event.getEntity());
    }

    public Entity getEntity()
    {
        return entity;
    }

    /**
     * EntityConstructing is fired when an Entity is being created. <br>
     * This event is fired within the constructor of the Entity.<br>
     * <br>
     * This event is not {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * <br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class EntityConstructing extends EntityEvent
    {
        public EntityConstructing(Entity entity)
        {
            super(entity);
        }

        public EntityConstructing(EntityEvent.EntityConstructing event) {
            this(event.getEntity());
        }

        public static Class<? extends Event> getForgeClass() {
            return EntityEvent.EntityConstructing.class;
        }
    }

    /**
     * This event is fired on server and client after an Entity has entered a different section. <br>
     * Sections are 16x16x16 block grids of the world.<br>
     * This event does not fire when a new entity is spawned, only when an entity moves from one section to another one.
     * Use {@link EntityJoinLevelEvent} to detect new entities joining the world.
     * <br>
     * This event is not {@link Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}
     * <br>
     * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class EnteringSection extends EntityEvent
    {

        private final long packedOldPos;
        private final long packedNewPos;

        public EnteringSection(Entity entity, long packedOldPos, long packedNewPos)
        {
            super(entity);
            this.packedOldPos = packedOldPos;
            this.packedNewPos = packedNewPos;
        }

        public EnteringSection(EntityEvent.EnteringSection event) {
            this(event.getEntity(), event.getPackedOldPos(), event.getPackedNewPos());
        }

        /**
         * A packed version of the old section's position. This is to be used with the various methods in {@link SectionPos},
         * such as {@link SectionPos#of(long)} or {@link SectionPos#x(long)} to avoid allocation.
         * @return the packed position of the old section
         */
        public long getPackedOldPos()
        {
            return packedOldPos;
        }

        /**
         * A packed version of the new section's position. This is to be used with the various methods in {@link SectionPos},
         * such as {@link SectionPos#of(long)} or {@link SectionPos#x(long)} to avoid allocation.
         * @return the packed position of the new section
         */
        public long getPackedNewPos()
        {
            return packedNewPos;
        }

        /**
         * @return the position of the old section
         */
        public SectionPos getOldPos()
        {
            return SectionPos.of(packedOldPos);
        }

        /**
         * @return the position of the new section
         */
        public SectionPos getNewPos()
        {
            return SectionPos.of(packedNewPos);
        }

        /**
         * Whether the chunk has changed as part of this event. If this method returns false, only the Y position of the
         * section has changed.
         */
        public boolean didChunkChange()
        {
            return SectionPos.x(packedOldPos) != SectionPos.x(packedNewPos) || SectionPos.z(packedOldPos) != SectionPos.z(packedNewPos);
        }

        public static Class<? extends Event> getForgeClass() {
            return EntityEvent.EnteringSection.class;
        }
    }

    public static Class<? extends Event> getForgeClass() {
        return EntityEvent.class;
    }
}
