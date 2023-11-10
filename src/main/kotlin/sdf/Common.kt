package geomx.sdf

import geomx.transform.Vector2D
import geomx.transform.Vector3D
import mathx.lerp
import mathx.round
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

internal fun abs(v: Vector2D) = Vector2D(abs(v.x), abs(v.y))
public fun abs(v: Vector3D): Vector3D = Vector3D(abs(v.x), abs(v.y), abs(v.z))

public fun max(v: Vector3D, s: Double): Vector3D = Vector3D(max(v.x, s), max(v.y, s), max(v.z, s))
internal fun max(v: Vector2D, s: Double) = Vector2D(max(v.x, s), max(v.y, s))
internal fun min(v: Vector2D, s: Vector2D) = Vector2D(min(v.x, s.x), min(v.y, s.x))

internal operator fun Vector2D.plus(s: Double) = Vector2D(x + s, y + s)
internal operator fun Vector3D.plus(s: Double) = Vector3D(x + s, y + s, z + s)
internal operator fun Vector3D.minus(s: Double) = Vector3D(x - s, y - s, z - s)
internal operator fun Vector2D.times(v: Vector2D) = Vector2D(x * v.x, y * v.y)
internal operator fun Vector3D.times(v: Vector3D) = Vector3D(x * v.x, y * v.y, z * v.z)
internal operator fun Vector2D.div(v: Vector2D) = Vector2D(x / v.x, y / v.y)
internal operator fun Vector3D.div(v: Vector3D) = Vector3D(x / v.x, y / v.y, z / v.z)

internal infix fun Vector2D.ndot(v: Vector2D) = x * v.x - y * v.y

public fun Vector3D.round(): Vector3D = Vector3D(
    round(x),
    round(y),
    round(z),
)

public fun Vector3D.round(b: Vector3D): Vector3D = Vector3D(
    round(x, b.x),
    round(y, b.y),
    round(z, b.z),
)

public fun Vector3D.coerceIn(min: Vector3D, max: Vector3D): Vector3D = Vector3D(
    x.coerceIn(min.x, max.x),
    y.coerceIn(min.y, max.y),
    z.coerceIn(min.z, max.z),
)


// Replacements

@Deprecated(":(")
internal fun clamp(x: Double, min: Double, max: Double) = x.coerceIn(min, max)

@Deprecated(":(")
internal fun length(v: Vector2D) = v.length()

@Deprecated(":(")
internal fun length(v: Vector3D) = v.length()

@Deprecated(":(")
internal fun dot(a: Vector2D, b: Vector2D) = a dot b

@Deprecated(":(")
internal fun dot(a: Vector3D, b: Vector3D) = a dot b

@Deprecated(":(")
internal fun cross(a: Vector3D, b: Vector3D) = a cross b

@Deprecated(":(")
internal fun dot2(v: Vector2D) = v dot v

@Deprecated(":(")
internal fun dot2(v: Vector3D) = v dot v

@Deprecated(":(")
internal fun ndot(a: Vector2D, b: Vector2D) = a ndot b

@Deprecated(":(")
internal val Vector3D.sxy get() = Vector2D(x, y)

@Deprecated(":(")
internal val Vector3D.sxz get() = Vector2D(x, z)

@Deprecated(":(")
internal val Vector3D.xyz get() = this

@Deprecated(":(")
internal val Vector3D.yzx get() = Vector3D(y, z, x)

@Deprecated(":(")
internal val Vector3D.zxy get() = Vector3D(z, x, y)

@Deprecated(":(")
internal fun clamp(v: Vector3D, min: Vector3D, max: Vector3D) = v.coerceIn(min, max)

@Deprecated(":(")
internal fun mix(x: Double, y: Double, a: Double) = lerp(a, x, y)

@Deprecated(":(")
internal fun round(a: Vector3D, b: Vector3D) = a.round(b)

@Deprecated(":(")
internal fun round(v: Vector3D): Vector3D = v.round()
