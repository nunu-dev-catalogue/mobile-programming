package dev.nunu.mobile.konkuk.intent

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.Optional

data class ApplicationInfo(
    val appLabel: String,
    val appClassName: String,
    val appPackageName: String,
    val appIcon: Drawable
)

@Composable
fun InstalledAppsScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val apps = rememberInstalledApps(context)
    LaunchedEffect(Unit) {
        scope.async {
            // TODO
        }.await()

        withContext(Dispatchers.IO) {

        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "201811218 이현우",
            fontSize = 40.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold
        )
        InstalledApps(apps)
    }
}

@Composable
fun InstalledApps(
    apps: List<ApplicationInfo>
) {
    LazyColumn {
        item {
            Text(
                text = "201811218 이현우",
                fontSize = 30.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold
            )
        }
        items(apps) { app ->
            App(app = app)
        }
    }
}

@Composable
fun App(
    app: ApplicationInfo
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .clickable {
                launchApp(context, app.appPackageName)
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = app.appIcon,
            contentDescription = "${app.appLabel} App",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = app.appLabel, fontSize = 20.sp)
            Text(text = app.appPackageName, fontSize = 14.sp, color = Color.Gray)

        }
    }
}


fun launchApp(context: Context, packageName: String) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    Optional.ofNullable(launchIntent)
        .ifPresentOrElse({
            context.startActivity(it)
        }, {
            Toast.makeText(context, "App not found", Toast.LENGTH_SHORT).show()
        })
}

@Composable
fun rememberInstalledApps(context: Context) = remember(context) {
    val packageManager = context.packageManager
    val intent = Intent(Intent.ACTION_MAIN, null)
        .addCategory(Intent.CATEGORY_LAUNCHER)
    val resolvedActivities = packageManager.queryIntentActivities(intent, 0)
    resolvedActivities
        .map {
            ApplicationInfo(
                appLabel = it.loadLabel(packageManager).toString(),
                appClassName = it.activityInfo.name,
                appPackageName = it.activityInfo.packageName,
                appIcon = it.loadIcon(packageManager)
            )
        }
}
