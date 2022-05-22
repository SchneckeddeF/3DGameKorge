import com.soywiz.kds.*
import com.soywiz.kds.iterators.*
import com.soywiz.klock.*
import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korge3d.*
import com.soywiz.korge3d.animation.*
import com.soywiz.korge3d.format.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korinject.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import com.soywiz.korev.Key
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.View
import com.soywiz.korge.view.addUpdater
import com.soywiz.korma.geom.degrees
//suspend fun main() = Demo3.main(args)
suspend fun main() = Korge(Korge.Config(module = Korge3DSampleModule()))

@Korge3DExperimental
class Korge3DSampleModule : KorgeModule(RootScene::class) {
	override val size: SizeInt = SizeInt(1280, 720)
	override val title: String = "KorGE 3D"
	override val bgcolor: RGBA = RGBA.float(.25f, .25f, .25f, 1f)

	override suspend fun AsyncInjector.configure() {
		mapPrototype { RootScene() }
	}
}
/*fun move_view_by_keys(stage: Stage?, view: Camera3D) {
	*//**Acces the "MainGameScene" per stage**//*
	view.addUpdater {
		var speed = 1.291286833710724
		if ((stage!!.input.keys.pressing(Key.A))){
			view.x -= speed
			view.rotation = 180.degrees
		}
		else if ((stage.input.keys.pressing(Key.D))){ view.x += speed
			view.rotation = 90.degrees}
		else if ((stage.input.keys.pressing(Key.W))) view.y -= speed
		else if ((stage.input.keys.pressing(Key.S))) view.y += speed
	}

}*/
class RootScene : Scene() {
	lateinit var contentSceneContainer: SceneContainer

	@OptIn(Korge3DExperimental::class)
	override suspend fun Container.sceneInit() {
		contentSceneContainer = sceneContainer(views)


		var tick = 0


		scene3D {
			val cube1 = cube()

			val cube3 = cube().position(-5, 0, 0)
			val cube4 = cube().position(+5, 0, 0)
			val cube5 = cube().position(0, -5, 0)
			val cube6 = cube().position(0, +5, 0)
			val cube7 = cube().position(0, 0, -5)
			val cube8 = cube().position(0, 0, +5)
			addUpdater {
				val angle = (tick / 4.0).degrees



				var speed = 1.291286833710724
				if ((stage!!.input.keys.pressing(Key.A))){
					camera.x -= speed
				}
				else if ((stage!!.keys.pressing(Key.D))){ camera.x += speed
					view.rotation = 90.degrees}
				else if ((stage!!.input.keys.pressing(Key.W))) camera.z -= speed
				else if ((stage!!.input.keys.pressing(Key.S))) camera.z += speed



				tick++
			}

			launchImmediately {
				while (true) {
					tween(time = 16.seconds) {
						cube1.modelMat.identity().rotate((it * 360).degrees, 0.degrees, 0.degrees)
					}
				}
			}




		}


/*
		contentSceneContainer.changeToDisablingButtons<CratesScene>(this)
		//contentSceneContainer.changeToDisablingButtons<SkinningScene>(this)*/
	}

//	inline fun <reified T : Scene> Container.sceneButton(title: String, x: Int) {
//		this += Button(title) { contentSceneContainer.changeToDisablingButtons<T>(this) }
//			.position(8 + x * 200, views.virtualHeight - 48)
//	}
}
/*

@Korge3DExperimental
class CratesScene : Scene() {
	override suspend fun Container.sceneInit() {
		val korgeTex = resourcesVfs["korge.png"].readNativeImage().mipmaps(false)
		val crateTex = resourcesVfs["crate.jpg"].readNativeImage().mipmaps(true)
		val crateMaterial = Material3D(diffuse = Material3D.LightTexture(crateTex))

		image(korgeTex).alpha(0.5)

		scene3D {
			//camera.set(fov = 60.degrees, near = 0.3, far = 1000.0)

			light().position(0, 0, -3)

			val cube1 = cube().material(crateMaterial)
			val cube2 = cube().position(0, 2, 0).scale(1, 2, 1).rotation(0.degrees, 0.degrees, 45.degrees).material(crateMaterial)
			val cube3 = cube().position(-5, 0, 0).material(crateMaterial)
			val cube4 = cube().position(+5, 0, 0).material(crateMaterial)
			val cube5 = cube().position(0, -5, 0).material(crateMaterial)
			val cube6 = cube().position(0, +5, 0).material(crateMaterial)
			val cube7 = cube().position(0, 0, -5).material(crateMaterial)
			val cube8 = cube().position(0, 0, +5).material(crateMaterial)

			var tick = 0
			addUpdater {
				val angle = (tick / 4.0).degrees
				camera.positionLookingAt(
					cos(angle * 2) * 4, cos(angle * 3) * 4, -sin(angle) * 4, // Orbiting camera
					0.0, 1.0, 0.0
				)
				tick++
			}

			launchImmediately {
				while (true) {
					tween(time = 16.seconds) {
						cube1.modelMat.identity().rotate((it * 360).degrees, 0.degrees, 0.degrees)
						cube2.modelMat.identity().rotate(0.degrees, (it * 360).degrees, 0.degrees)
					}
				}
			}
		}

		image(korgeTex).position(views.virtualWidth, 0).anchor(1, 0).alpha(0.5)
	}
}

@Korge3DExperimental
class MonkeyScene : Scene() {
	override suspend fun Container.sceneInit() {
		//delay(10.seconds)
		//println("delay")
		scene3D {
			val light1 = light().position(0, 10, +10).setTo(Colors.RED)
			val light2 = light().position(10, 0, +10).setTo(Colors.BLUE)

			launchImmediately {
				while (true) {
					tween(light1::y[-20], light2::x[-20], time = 1.seconds, easing = Easing.SMOOTH)
					tween(light1::y[+20], light2::x[+20], time = 1.seconds, easing = Easing.SMOOTH)
				}
			}

			val library = resourcesVfs["monkey-smooth.dae"].readColladaLibrary()
			val model = library.geometryDefs.values.first()
			val view = mesh(model.mesh).rotation(-90.degrees, 0.degrees, 0.degrees)

			var tick = 0
			addUpdater {
				val angle = (tick / 1.0).degrees
				camera.positionLookingAt(
					cos(angle * 1) * 4, 0.0, -sin(angle * 1) * 4, // Orbiting camera
					0.0, 0.0, 0.0
				)
				tick++
			}
		}
	}

}

@Korge3DExperimental
class SkinningScene : Scene() {
	override suspend fun Container.sceneInit() {
		scene3D {
			//val library = resourcesVfs["model.dae"].readColladaLibrary()
			//val library = resourcesVfs["ball.dae"].readColladaLibrary()
			val library = resourcesVfs["skinning.dae"].readColladaLibrary()
			//val library = resourcesVfs["model_skinned_animated.dae"].readColladaLibrary()
			//val library = resourcesVfs["Fallera.dae"].readColladaLibrary()

			val mainSceneView = library.mainScene.instantiate()
			val cameras = mainSceneView.findByType<Camera3D>()

			val animators = library.animationDefs.values.map { Animator3D(it, mainSceneView) }
			addUpdater { animators.fastForEach { animator -> animator.update(it) } }
			val model = mainSceneView.findByType<ViewWithMesh3D>().first()
			//.rotation(-90.degrees, 90.degrees, 0.degrees)

			val camera1 = cameras.firstOrNull() ?: camera
			val camera2 = cameras.lastOrNull() ?: camera

			camera = camera1.clone()

			this += mainSceneView
			addUpdater {
				//val mainSceneView = mainSceneView
				//println(mainSceneView)

				//println("Camera: ${camera.transform}")
				//println("Model: ${model.transform}")
				//println("Skeleton: ${model.skeleton}")
			}
		}
	}

}*/
