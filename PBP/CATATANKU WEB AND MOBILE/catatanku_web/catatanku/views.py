from django.shortcuts import render
from catatanku.models import Notes
from catatanku.forms import NotesForm
from django.http import HttpResponse, JsonResponse, HttpResponseRedirect
from django.core import serializers
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


def big_boi(request):
    form = NotesForm(request.POST or None)

    if form.is_valid() and request.method == "POST":
        form.save()
        return HttpResponseRedirect(reverse('catatanku:show_main'))

    context = {'form': form}
    return render(request, "create.html", context)



@csrf_exempt
def create_note(request):  
# create object of form
    form = NotesForm(request.POST or None)

    if form.is_valid() and request.method == "POST":
        form.save()
        data = NotesForm.objects.last()

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
    
    #return JsonResponse({'error': 'Invalid request'})