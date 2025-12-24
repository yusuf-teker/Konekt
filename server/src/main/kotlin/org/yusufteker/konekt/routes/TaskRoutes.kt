package org.yusufteker.konekt.routes

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.yusufteker.konekt.data.entity.TaskEntity
import org.yusufteker.konekt.data.mapper.toDomain
import org.yusufteker.konekt.data.repository.TaskRepository
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.request.CreateTaskRequest
import org.yusufteker.konekt.domain.models.request.UpdateTaskRequest

fun Route.taskRoutes(taskRepository: TaskRepository) {

    authenticate("auth-jwt") {
        route("/tasks") {

            // 🧩 Görev oluştur
            post {

                // ✅ JWT'den kullanıcı kimliğini al
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                    ?: return@post call.respond(HttpStatusCode.Unauthorized)

                // receive ile (content)body'yi kotlin objesine dönüştür
                val request = call.receive<CreateTaskRequest>()

                // DB'ye eklemek üzere repositorye bilgileri veri
                val task = taskRepository.createTask(
                    title = request.title,
                    description = request.description,
                    status = request.status.name,
                    priority = request.priority.name,
                    dueDate = request.dueDate,
                    reminderTime = request.reminderTime,
                    isRecurring = request.isRecurring,
                    recurrencePattern = request.recurrencePattern?.name,
                    recurrenceConfigJson = request.recurrenceConfig?.let { taskRepository.json.encodeToString(it) },
                    colorTag = request.colorTag,
                    tags = request.tags,
                    attachments = request.attachments,
                    subtasks = request.subtasks,
                    createdBy = userId
                )

                if (task == null)
                    call.respond(HttpStatusCode.InternalServerError, "Görev oluşturulamadı")
                else
                    call.respond(HttpStatusCode.Created, task.toDomain())
            }

            // 🔍 Kullanıcının görevlerini getir
            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                    ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val tasks: List<TaskEntity> = taskRepository.getTasksByUser(userId)
                call.respond<List<Task>>(tasks.map { it.toDomain() })
            }

            // 🔍 ID'ye göre görev getir
            get("/{id}") {
                val taskId = call.parameters["id"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Geçersiz id")

                val task = taskRepository.getTaskById(taskId)
                if (task == null) call.respond(HttpStatusCode.NotFound)
                else call.respond(task.toDomain())
            }

            // ✏️ Görev güncelle
            put("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                    ?: return@put call.respond(HttpStatusCode.Unauthorized)

                val taskId = call.parameters["id"]
                    ?: return@put call.respond(HttpStatusCode.BadRequest, "Geçersiz id")

                val request = call.receive<UpdateTaskRequest>()
                val success = taskRepository.updateTask(
                    taskId = taskId,
                    title = request.title,
                    description = request.description,
                    status = request.status?.name,
                    priority = request.priority?.name,
                    updatedBy = userId
                )

                if (success) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }

            // 🗑️ Görevi arşivle
            delete("/{id}") {
                val taskId = call.parameters["id"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Geçersiz id")

                val success = taskRepository.archiveTask(taskId)
                if (success) call.respond(HttpStatusCode.NoContent)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
