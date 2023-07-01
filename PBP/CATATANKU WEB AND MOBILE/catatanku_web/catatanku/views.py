import json
from django.shortcuts import render
from catatanku.models import Notes
from catatanku.forms import NotesForm
from django.http import HttpResponse, JsonResponse, HttpResponseRedirect
from django.core import serializers
from django.views.generic import View
from django.views.decorators.csrf import csrf_exempt
from django.urls import reverse


# Create your views here.
def show_main(request):
    notes_data = Notes.objects.all()
    context = {
        'list_of_notes': notes_data,
    }
    return render(request, "main.html", context)

def show_json(request):
    data = Notes.objects.all()
    return HttpResponse(serializers.serialize("json", data), content_type="application/json")

def show_json_by_id(request, id):
    data = Notes.objects.filter(pk=id)
    return HttpResponse(serializers.serialize("json", data), content_type="applicaton/json")

class Edit(View):
    def get(self, request):
        selectedId = request.GET.get('id', None)
        newTitle = request.GET.get('title', None)
        newDescription = request.GET.get('description', None)

        updatedNote = Notes.objects.get(id=selectedId)
        updatedNote.title = newTitle
        updatedNote.description = newDescription
        updatedNote.save()

        data = { 
            'edited':True,
            'newTitle': newTitle,
            'newDescription':newDescription,
        }
        return JsonResponse(data)

class Delete(View):
    def  get(self, request):
        id1 = request.GET.get('id', None)
        Notes.objects.get(id=id1).delete()
        data = {
            'deleted': True
        }
        return JsonResponse(data)


@csrf_exempt
def create_note(request):  
# create object of form
    form = NotesForm(request.POST or None)

    if form.is_valid() and request.method == "POST":
        form.save()
        data = Notes.objects.last()

        # parsing the form data into json
        result = {
            'id':data.id,
            'title':data.title,
            'description':data.description,
            'date': data.date,
            'time': data.time,
        }
        return JsonResponse(result)
    
    context = {'form': form}
    return render(request, "create.html", context)

@csrf_exempt
def create_note_flutter(request):
    if request.method == 'POST':

        data = json.loads(request.body)

        new_note = Notes.objects.create(
            title = data["title"],
            description = data["description"]
        )

        new_note.save()

        return JsonResponse({"status": "success"}, status=200)
    else:
        return JsonResponse({"status": "error"}, status=401)

@csrf_exempt
def delete_note_flutter(request, id):
    if request.method == 'DELETE':
        selectedId = id
        Notes.objects.get(id=selectedId).delete()
        return JsonResponse({"status": "success"}, status = 200)
    else:
        return JsonResponse({"status": "error"}, status=401)

@csrf_exempt
def edit_note_flutter(request, id):
    if request.method == 'PUT':
        selectedId = id
        data = json.loads(request.body)

        updatedNote = Notes.objects.get(id=selectedId)
        updatedNote.title = data["title"]
        updatedNote.description = data["description"]
        updatedNote.save()
        return JsonResponse({"status": "success"}, status = 200)
    else:
        return JsonResponse({"status": "error"}, status=401)