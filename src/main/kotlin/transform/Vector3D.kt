package geomx.transform

import mathx.length
import mathx.lerp

public data class Vector3D(
    public val x: Double,
    public val y: Double,
    public val z: Double,
) : Transformation<Vector3D> {
    override val tx: Double get() = x
    override val ty: Double get() = y
    override val tz: Double get() = z
    override val tw: Double get() = 0.0

    public operator fun plus(v: Vector3D): Vector3D = Vector3D(x = x + v.x, y = y + v.y, z = z + v.z)
    public operator fun minus(v: Vector3D): Vector3D = Vector3D(x = x - v.x, y = y - v.y, z = z - v.z)
    public operator fun times(s: Double): Vector3D = Vector3D(x = x * s, y = y * s, z = z * s)
    public operator fun div(s: Double): Vector3D = Vector3D(x = x / s, y = y / s, z = z / s)

    @JvmName("negate")
    public operator fun unaryMinus(): Vector3D = Vector3D(x = -x, y = -y, z = -z)

    public fun length(): Double = length(x = x, y = y, z = z)

    public fun normalize(): Vector3D {
        val len = length()
        return if (len == 0.0) ZERO else div(len)
    }

    public infix fun dot(v: Vector3D): Double = x * v.x + y * v.y + z * v.z

    public infix fun cross(v: Vector3D): Vector3D = Vector3D(
        x = y * v.z - z * v.y,
        y = z * v.x - x * v.z,
        z = x * v.y - y * v.x,
    )

    public fun interpolate(b: Vector3D, t: Double): Vector3D = Vector3D(
        x = lerp(x, b.x, t),
        y = lerp(y, b.y, t),
        z = lerp(z, b.z, t),
    )

    override fun transformBy(t: Transformation<*>): Vector3D = Vector3D(
        x = t tx this,
        y = t ty this,
        z = t tz this,
    )

    public companion object : Transformation.Builder<Vector3D> {
        public val ZERO: Vector3D = Vector3D(x = 0.0, y = 0.0, z = 0.0)
        public val X: Vector3D = x()
        public val Y: Vector3D = y()
        public val Z: Vector3D = z()

        public val POSITIVE_INFINITY: Vector3D = Vector3D(
            x = Double.POSITIVE_INFINITY,
            y = Double.POSITIVE_INFINITY,
            z = Double.POSITIVE_INFINITY,
        )
        public val NEGATIVE_INFINITY: Vector3D = Vector3D(
            x = Double.NEGATIVE_INFINITY,
            y = Double.NEGATIVE_INFINITY,
            z = Double.NEGATIVE_INFINITY,
        )
        public val NaN: Vector3D = Vector3D(
            x = Double.NaN,
            y = Double.NaN,
            z = Double.NaN,
        )

        public fun x(x: Double = 1.0): Vector3D = Vector3D(x = x, y = 0.0, z = 0.0)
        public fun y(y: Double = 1.0): Vector3D = Vector3D(x = 0.0, y = y, z = 0.0)
        public fun z(z: Double = 1.0): Vector3D = Vector3D(x = 0.0, y = 0.0, z = z)

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Vector3D = Vector3D(x = tx, y = ty, z = tz)

        override fun toString(): String = "Vector3D"
    }
}
