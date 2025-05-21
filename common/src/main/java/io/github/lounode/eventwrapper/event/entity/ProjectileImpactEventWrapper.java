package io.github.lounode.eventwrapper.event.entity;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.Event;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.github.lounode.eventwrapper.eventbus.api.Cancelable;

/**
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
 * This event is fired when a projectile entity impacts something.<br>
 * This event is fired via {@link ForgeEventFactory#onProjectileImpact(Projectile, HitResult)}
 * This event is fired for all vanilla projectiles by Forge,
 * custom projectiles should fire this event and check the result in a similar fashion.
 * This event is cancelable. When canceled, the impact will not be processed and the projectile will continue flying.
 * Killing or other handling of the entity after event cancellation is up to the modder.
 * You can also directly set the {@link ProjectileImpactEventWrapper.ImpactResult} to change the impact behaviour.
 * 
 * @see #setImpactResult(ProjectileImpactEventWrapper.ImpactResult)
 */
@Cancelable
public class ProjectileImpactEventWrapper extends EntityEventWrapper {

	private final HitResult ray;
	private final Projectile projectile;

	private ImpactResult result = ImpactResult.DEFAULT;

	public ProjectileImpactEventWrapper(Projectile projectile, HitResult ray) {
		super(projectile);
		this.ray = ray;
		this.projectile = projectile;
	}

	public ProjectileImpactEventWrapper(ProjectileImpactEvent event) {
		this(event.getProjectile(), event.getRayTraceResult());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated Use {@link #setImpactResult(ProjectileImpactEventWrapper.ImpactResult)} instead.
	 */
	@Deprecated(forRemoval = true, since = "1.20.1")
	@Override
	public void setCanceled(boolean cancel) {
		super.setCanceled(cancel);
	}

	public HitResult getRayTraceResult() {
		return ray;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public void setImpactResult(@NotNull ImpactResult newResult) {
		this.result = Objects.requireNonNull(newResult, "ImpactResult cannot be null");
	}

	public ImpactResult getImpactResult() {
		return result;
	}

	public enum ImpactResult {
		/**
		 * The default behaviour, the projectile will be destroyed and the hit will be processed.
		 */
		DEFAULT,
		/**
		 * The projectile will pass through the current entity as if it wasn't there. This will return default behaviour
		 * if there is no entity.
		 */
		SKIP_ENTITY,
		/**
		 * Damage the entity and stop the projectile here, the projectile will not pierce.
		 */
		STOP_AT_CURRENT,
		/**
		 * Cancel the piercing aspect of the projectile, and do not damage the entity.
		 */
		STOP_AT_CURRENT_NO_DAMAGE
	}

	public static Class<? extends Event> getForgeClass() {
		return ProjectileImpactEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new ProjectileImpactEvent(getProjectile(), getRayTraceResult());
	}
}
