package geomx.transform

import mathx.length
import mathx.lerp

public data class Point3D(
    public val x: Double,
    public val y: Double,
    public val z: Double,
) : Transformation<Point3D> {
    override val tx: Double get() = x
    override val ty: Double get() = y
    override val tz: Double get() = z

    public operator fun plus(v: Point3D): Point3D = Point3D(x = x + v.x, y = y + v.y, z = z + v.z)
    public operator fun minus(v: Point3D): Point3D = Point3D(x = x - v.x, y = y - v.y, z = z - v.z)
    public operator fun times(s: Double): Point3D = Point3D(x = x * s, y = y * s, z = z * s)
    public operator fun div(s: Double): Point3D = Point3D(x = x / s, y = y / s, z = z / s)

    @JvmName("negate")
    public operator fun unaryMinus(): Point3D = Point3D(x = -x, y = -y, z = -z)

    public fun distanceTo(p: Point3D): Double = length(x = p.x - x, y = p.y - y, z = p.z - z)

    public infix fun dot(v: Point3D): Double = x * v.x + y * v.y + z * v.z

    public fun interpolate(b: Point3D, t: Double): Point3D = Point3D(
        x = lerp(x, b.x, t),
        y = lerp(y, b.y, t),
        z = lerp(z, b.z, t),
    )

    override fun transformBy(t: Transformation<*>): Point3D = Point3D(
        x = t tx this,
        y = t ty this,
        z = t tz this,
    )

    public companion object : Transformation.Builder<Point3D> {
        public val ZERO: Point3D = Point3D(x = 0.0, y = 0.0, z = 0.0)
        public val X: Point3D = x()
        public val Y: Point3D = y()
        public val Z: Point3D = z()

        public val POSITIVE_INFINITY: Point3D = Point3D(
            x = Double.POSITIVE_INFINITY,
            y = Double.POSITIVE_INFINITY,
            z = Double.POSITIVE_INFINITY,
        )
        public val NEGATIVE_INFINITY: Point3D = Point3D(
            x = Double.NEGATIVE_INFINITY,
            y = Double.NEGATIVE_INFINITY,
            z = Double.NEGATIVE_INFINITY,
        )
        public val NaN: Point3D = Point3D(
            x = Double.NaN,
            y = Double.NaN,
            z = Double.NaN,
        )

        public fun x(x: Double = 1.0): Point3D = Point3D(x = x, y = 0.0, z = 0.0)
        public fun y(y: Double = 1.0): Point3D = Point3D(x = 0.0, y = y, z = 0.0)
        public fun z(z: Double = 1.0): Point3D = Point3D(x = 0.0, y = 0.0, z = z)

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Point3D = Point3D(x = tx, y = ty, z = tz)

        override fun toString(): String = "Vector3D"
    }
}
