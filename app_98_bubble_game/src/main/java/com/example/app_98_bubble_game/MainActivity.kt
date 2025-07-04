package com.example.bubblegame

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import com.example.app_98_bubble_game.ui.theme.BubbleGameTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

// MainActivity: 앱의 진입점. setContent를 통해 Compose UI를 설정합니다.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BubbleGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 최종 버전의 게임 화면을 표시합니다.
                    BubbleGameStep5()
                }
            }
        }
    }
}

// 데이터 클래스: 개별 버블의 상태를 저장합니다.
data class Bubble(
    val id: Int,
    val position: Offset,
    val radius: Float,
    val color: Color,
    val creationTime: Long = System.currentTimeMillis()
)

// 게임 상태를 관리하는 클래스
class GameState {
    var bubbles by mutableStateOf<List<Bubble>>(emptyList())
    var score by mutableStateOf(0)
    var isGameOver by mutableStateOf(false)
    var gameTime by mutableStateOf(30) // 30초 제한 시간
}

/**
 * Step 1: 기본 버블 게임
 * - Canvas에 버블을 그립니다.
 * - 버블은 무작위 위치와 색상으로 나타납니다.
 * - 사용자가 버블을 터치하면 사라지고 점수가 올라갑니다.
 * - 일정 시간이 지나면 버블이 자동으로 사라집니다.
 */
@Composable
fun BubbleGameStep1(gameState: GameState = remember { GameState() }) {
    val coroutineScope = rememberCoroutineScope()

    // 게임 루프: 게임이 끝나지 않았다면 1초마다 새 버블을 생성합니다.
    LaunchedEffect(gameState.isGameOver) {
        if (!gameState.isGameOver) {
            launch {
                while (true) {
                    delay(1000) // 1초마다 실행
                    // 3초가 지난 버블 제거
                    val currentTime = System.currentTimeMillis()
                    gameState.bubbles = gameState.bubbles.filter {
                        currentTime - it.creationTime < 3000
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // 터치된 버블을 찾아 제거하고 점수를 올립니다.
                        gameState.bubbles.reversed().forEach { bubble ->
                            val distance = sqrt(
                                (offset.x - bubble.position.x).pow(2) +
                                        (offset.y - bubble.position.y).pow(2)
                            )
                            if (distance <= bubble.radius) {
                                gameState.bubbles = gameState.bubbles - bubble
                                gameState.score++
                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {
            // 새 버블 생성 (랜덤)
            if (Random.nextFloat() < 0.1f && gameState.bubbles.size < 15) {
                val newBubble = Bubble(
                    id = Random.nextInt(),
                    position = Offset(
                        x = Random.nextFloat() * size.width,
                        y = Random.nextFloat() * size.height
                    ),
                    radius = Random.nextFloat() * 50 + 50,
                    color = Color(
                        red = Random.nextInt(256),
                        green = Random.nextInt(256),
                        blue = Random.nextInt(256),
                        alpha = 200
                    )
                )
                gameState.bubbles = gameState.bubbles + newBubble
            }

            // 모든 버블을 화면에 그립니다.
            gameState.bubbles.forEach { bubble ->
                drawCircle(
                    color = bubble.color,
                    radius = bubble.radius,
                    center = bubble.position
                )
            }
        }

        // 점수 표시
        Text(
            text = "Score: ${gameState.score}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Step 1: 버블 게임")
@Composable
fun BubbleGameStep1Preview() {
    BubbleGameTheme {
        BubbleGameStep1()
    }
}


/**
 * Step 2: 다이얼로그 추가
 * - 게임에 시간 제한을 둡니다.
 * - 시간이 다 되면 게임이 종료되고, AlertDialog로 최종 점수를 보여줍니다.
 * - '다시 시작' 버튼으로 게임을 재시작할 수 있습니다.
 */
@Composable
fun BubbleGameStep2(gameState: GameState = remember { GameState() }) {

    // 게임 타이머
    LaunchedEffect(gameState.isGameOver) {
        if (!gameState.isGameOver) {
            while (gameState.gameTime > 0) {
                delay(1000)
                gameState.gameTime--
            }
            gameState.isGameOver = true
        }
    }

    // 기본 게임 UI
    BubbleGameStep1(gameState)

    // 게임 오버 시 다이얼로그 표시
    if (gameState.isGameOver) {
        AlertDialog(
            onDismissRequest = { /* 다이얼로그 밖을 터치해도 닫히지 않음 */ },
            title = { Text("게임 종료!") },
            text = { Text("최종 점수: ${gameState.score}") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // 게임 상태 초기화 및 재시작
                        gameState.score = 0
                        gameState.bubbles = emptyList()
                        gameState.gameTime = 30
                        gameState.isGameOver = false
                    }
                ) {
                    Text("다시 시작")
                }
            }
        )
    }

    // 남은 시간 표시
    Text(
        text = "Time: ${gameState.gameTime}",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 50.dp, start = 16.dp)
    )
}

@Preview(showBackground = true, name = "Step 2: 다이얼로그 추가")
@Composable
fun BubbleGameStep2Preview() {
    BubbleGameTheme {
        BubbleGameStep2()
    }
}


/**
 * Step 3: 소리 추가
 * - SoundPool을 사용하여 버블이 터질 때 '뽁'하는 소리를 재생합니다.
 * - res/raw 폴더에 효과음 파일(예: pop_sound.mp3)이 필요합니다.
 */
@Composable
fun BubbleGameStep3(gameState: GameState = remember { GameState() }) {
    val context = LocalContext.current
    // SoundPool 초기화
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(5).build()
    }
    // 사운드 로드. (주의: R.raw.pop_sound 파일이 프로젝트에 있어야 합니다.)
    val popSoundId = remember {
        // try-catch로 리소스가 없을 경우 앱 충돌 방지
        try {
            soundPool.load(context, R.raw.pop_sound, 1)
        } catch (e: Exception) {
            Log.e("BubbleGame", "Sound resource not found. Place 'pop_sound.mp3' in res/raw.")
            0
        }
    }

    // 소리 재생 함수
    fun playPopSound() {
        if (popSoundId != 0) {
            soundPool.play(popSoundId, 1f, 1f, 0, 0, 1f)
        }
    }

    // 메모리 누수 방지를 위해 Composable이 해제될 때 soundPool도 해제
    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }
    
    // 기본 게임 UI (Step 2)
    BubbleGameStep2(gameState)

    // 버블 터치 시 소리 재생 로직 추가
    // (기존 로직이 복잡해지므로, 여기서는 개념만 설명하고 Step5에서 통합 구현합니다)
    // 실제 구현은 BubbleGameStep1의 detectTapGestures 내에서 score 증가와 함께 playPopSound()를 호출하면 됩니다.
    // 예를 들어, 아래와 같이 수정됩니다:
    /*
    if (distance <= bubble.radius) {
        gameState.bubbles = gameState.bubbles - bubble
        gameState.score++
        playPopSound() // <-- 소리 재생!
        return@detectTapGestures
    }
    */
}

@Preview(showBackground = true, name = "Step 3: 소리 추가")
@Composable
fun BubbleGameStep3Preview() {
    BubbleGameTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(16.dp)) {
           Text("Step 3: 소리 추가", style = MaterialTheme.typography.headlineSmall)
           Text("버블을 터뜨릴 때 소리가 납니다.\n(미리보기에서는 실제 소리가 나지 않을 수 있습니다.)", modifier = Modifier.padding(16.dp))
           // 실제 게임에서는 BubbleGameStep5를 실행하여 확인
           BubbleGameStep2()
        }
    }
}


/**
 * Step 4: 진동 알림 추가
 * - VibratorManager를 사용하여 버블이 터질 때 짧은 진동을 발생시킵니다.
 * - AndroidManifest.xml에 <uses-permission android:name="android.permission.VIBRATE" /> 권한이 필요합니다.
 */
@Composable
fun BubbleGameStep4(gameState: GameState = remember { GameState() }) {
    val context = LocalContext.current
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    // 진동 효과 함수
    fun vibrate() {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(50)
            }
        }
    }

    // 기본 게임 UI (Step 2)
    BubbleGameStep2(gameState)
    
    // 버블 터치 시 진동 발생 로직 추가
    // (이 또한 Step 5에서 통합 구현합니다.)
    /*
    if (distance <= bubble.radius) {
        gameState.bubbles = gameState.bubbles - bubble
        gameState.score++
        playPopSound()
        vibrate() // <-- 진동 발생!
        return@detectTapGestures
    }
    */
}

@Preview(showBackground = true, name = "Step 4: 진동 알림 추가")
@Composable
fun BubbleGameStep4Preview() {
    BubbleGameTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Step 4: 진동 추가", style = MaterialTheme.typography.headlineSmall)
            Text("버블을 터뜨릴 때 진동이 울립니다.\n(미리보기에서는 실제 진동이 오지 않을 수 있습니다.)", modifier = Modifier.padding(16.dp))
            // 실제 게임에서는 BubbleGameStep5를 실행하여 확인
            BubbleGameStep2()
        }
    }
}


/**
 * Step 5: 알림 띄우기 (모든 기능 통합)
 * - 사용자가 제공한 알림 코드를 Compose 환경에 맞게 수정하여 통합합니다.
 * - 게임 종료 후 '알림 보내기' 버튼을 통해 알림을 보낼 수 있습니다.
 * - 알림 권한을 요청하는 로직을 포함합니다.
 */
@Composable
fun BubbleGameStep5() {
    val context = LocalContext.current
    val gameState = remember { GameState() }

    // --- Sound & Vibration Setup ---
    val soundPool = remember { SoundPool.Builder().setMaxStreams(5).build() }
    val popSoundId = remember {
        try {
            soundPool.load(context, R.raw.pop_sound, 1)
        } catch (e: Exception) { 0 }
    }
    val vibrator = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
    
    DisposableEffect(Unit) { onDispose { soundPool.release() } }

    fun playPopEffect() {
        if (popSoundId != 0) soundPool.play(popSoundId, 1f, 1f, 0, 0, 1f)
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(50)
            }
        }
    }
    
    // --- Notification Setup ---
    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 권한이 승인되면 알림 표시
            showNotification(context, gameState.score)
        } else {
            Log.w("BubbleGame", "Notification permission denied.")
        }
    }

    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) -> {
                    showNotification(context, gameState.score)
                }
                else -> {
                    notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            showNotification(context, gameState.score)
        }
    }

    // --- Game Logic & UI ---
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = gameState.isGameOver) {
        if (!gameState.isGameOver) {
            // 게임 타이머
            launch {
                while (gameState.gameTime > 0 && !gameState.isGameOver) {
                    delay(1000)
                    gameState.gameTime--
                }
                if (!gameState.isGameOver) {
                    gameState.isGameOver = true
                }
            }
            // 버블 자동 제거
            launch {
                while (!gameState.isGameOver) {
                    delay(1000)
                    val currentTime = System.currentTimeMillis()
                    gameState.bubbles = gameState.bubbles.filter { currentTime - it.creationTime < 3000 }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(gameState.isGameOver) {
                    if (!gameState.isGameOver) {
                        detectTapGestures { offset ->
                            coroutineScope.launch {
                                val tappedBubble = gameState.bubbles.lastOrNull { bubble ->
                                    val distance = sqrt((offset.x - bubble.position.x).pow(2) + (offset.y - bubble.position.y).pow(2))
                                    distance <= bubble.radius
                                }
                                if (tappedBubble != null) {
                                    gameState.bubbles = gameState.bubbles - tappedBubble
                                    gameState.score++
                                    playPopEffect()
                                }
                            }
                        }
                    }
                }
        ) {
            // 새 버블 생성
            if (!gameState.isGameOver && Random.nextFloat() < 0.2f && gameState.bubbles.size < 15) {
                gameState.bubbles = gameState.bubbles + Bubble(
                    id = Random.nextInt(),
                    position = Offset(Random.nextFloat() * size.width, Random.nextFloat() * size.height),
                    radius = Random.nextFloat() * 50 + 50,
                    color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), 200)
                )
            }
            // 버블 그리기
            gameState.bubbles.forEach { bubble ->
                drawCircle(color = bubble.color, radius = bubble.radius, center = bubble.position)
            }
        }

        // UI 요소
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Score: ${gameState.score}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Time: ${gameState.gameTime}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        if (gameState.isGameOver) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("게임 종료!") },
                text = { Text("최종 점수: ${gameState.score}") },
                confirmButton = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { requestNotificationPermission() }) {
                            Text("결과 알림 받기")
                        }
                        TextButton(
                            onClick = {
                                gameState.score = 0
                                gameState.bubbles = emptyList()
                                gameState.gameTime = 30
                                gameState.isGameOver = false
                            }
                        ) {
                            Text("다시 시작")
                        }
                    }
                }
            )
        }
    }
}

// 알림을 생성하고 표시하는 함수
fun showNotification(context: Context, score: Int) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val builder: NotificationCompat.Builder

    val channelId = "bubble-game-channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelName = "Bubble Game Channel"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "버블 게임 관련 알림 채널입니다."
            setShowBadge(true)
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            setSound(uri, audioAttributes)
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
        builder = NotificationCompat.Builder(context, channelId)
    } else {
        builder = NotificationCompat.Builder(context)
    }

    // RemoteInput (답장 기능) 설정
    val KEY_TEXT_REPLY = "key_text_reply"
    val replyLabel = "메시지 보내기"
    val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
        setLabel(replyLabel)
        build()
    }
    val replyIntent = Intent(context, ReplyReceiver::class.java)
    val replyPendingIntent = PendingIntent.getBroadcast(
        context, 30, replyIntent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
    )

    builder.run {
        setSmallIcon(R.drawable.ic_bubble) // (주의: res/drawable/ic_bubble.xml 아이콘 파일 필요)
        setWhen(System.currentTimeMillis())
        setContentTitle("버블 게임 결과!")
        setContentText("축하합니다! 최종 점수는 ${score}점입니다.")
        // (주의: res/drawable/ic_game_large.png 이미지 파일 필요)
        val largeIcon = try { BitmapFactory.decodeResource(context.resources, R.drawable.ic_game_large) } catch (e: Exception) { null }
        setLargeIcon(largeIcon)
        setAutoCancel(true)
        addAction(
            NotificationCompat.Action.Builder(
                R.drawable.ic_send, // (주의: res/drawable/ic_send.xml 아이콘 파일 필요)
                "답장", replyPendingIntent
            ).addRemoteInput(remoteInput).build()
        )
    }

    manager.notify(11, builder.build())
}


@Preview(showBackground = true, name = "Step 5: 모든 기능 통합")
@Composable
fun BubbleGameStep5Preview() {
    BubbleGameTheme {
        BubbleGameStep5()
    }
}

// 알림의 답장을 처리하는 BroadcastReceiver
class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 사용자가 입력한 텍스트를 가져옵니다.
        val replyTxt = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")
        Log.d("BubbleGame-Reply", "Received Reply: $replyTxt")

        // 알림을 닫습니다.
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(11)

        // TODO: 답장 받은 내용을 처리하는 로직 추가 (예: 서버 전송, DB 저장 등)
    }
}
