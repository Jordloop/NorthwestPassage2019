package controllers;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.Random;

public class GameController extends Controller
{
    private FormFactory formFactory;
    final private String PLAYER_NAME_SESSION_KEY = "PLAYER_KEY";
    final private String KITTEN_IMG_SESSION_KEY = "KITTEN_IMG";
    final private String CREW_MEMBERS_SESSION_KEY = "CREW_MEMBERS";
    final private String[] CREW_NAMES = new String[]{"Larry", "Doug", "Mark", "Dirk", "Tree",
                                                      "Shane", "Nathan", "Harvy", "Lance", "Neil"};

    @Inject
    public GameController(FormFactory formFactory)
    {
        this.formFactory = formFactory;
    }

    public Result getWelcome()
    {
        return ok(views.html.welcome.render());
    }

    public Result getKittens()
    {
        return ok(views.html.kittens.render());
    }

    public Result postStart(Http.Request request)
    {
        DynamicForm form = formFactory.form().bindFromRequest(request);

        final String PLAYER_NAME = form.get("playerName");
        final String KITTEN_IMG = form.get("kitten");
        final String CREW_COUNT = "10";
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));

        return ok(views.html.start.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES))
                .addingToSession(request, PLAYER_NAME_SESSION_KEY, PLAYER_NAME)
                .addingToSession(request, KITTEN_IMG_SESSION_KEY, KITTEN_IMG)
                .addingToSession(request, CREW_MEMBERS_SESSION_KEY, CREW_COUNT);
    }

    public Result postHomePort(Http.Request request)
    {
        final String PLAYER_NAME = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        final String KITTEN_IMG = request.session().getOptional(KITTEN_IMG_SESSION_KEY).orElse("Unknown");
        final String CREW_COUNT = request.session().getOptional(CREW_MEMBERS_SESSION_KEY).orElse("Unknown");
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));

        return ok(views.html.homeport.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES));
    }

    public Result postEastFromEngland(Http.Request request)
    {
        final String PLAYER_NAME = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        final String KITTEN_IMG = request.session().getOptional(KITTEN_IMG_SESSION_KEY).orElse("Unknown");
        final String CREW_COUNT = decrementCrewCount(request);
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));
        final String DEAD_MEMBER = CREW_NAMES[Integer.parseInt(CREW_COUNT)];

        if (Integer.parseInt(CREW_COUNT) < 5)
        {
            return ok(views.html.oakisland.render(PLAYER_NAME, CREW_COUNT , KITTEN_IMG, CREW_NAMES))
                                          .addingToSession(request, CREW_MEMBERS_SESSION_KEY, CREW_COUNT);
        }
        else
        {
            return ok(views.html.eastfromengland.render(CREW_COUNT, DEAD_MEMBER, KITTEN_IMG, CREW_NAMES))
                                                .addingToSession(request, CREW_MEMBERS_SESSION_KEY, CREW_COUNT);
        }
    }

    public Result postEastEnd(Http.Request request)
    {
        final String PLAYER_NAME = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        final String KITTEN_IMG = request.session().getOptional(KITTEN_IMG_SESSION_KEY).orElse("Unknown");
        final String CREW_COUNT = request.session().getOptional(CREW_MEMBERS_SESSION_KEY).orElse("Unknown");
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));

        return ok(views.html.eastend.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES));
    }

    public Result postWestFromEngland(Http.Request request)
    {
        final String PLAYER_NAME = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        final String KITTEN_IMG = request.session().getOptional(KITTEN_IMG_SESSION_KEY).orElse("Unknown");
        final String CREW_COUNT = decrementCrewCount(request);
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));

        if (Integer.parseInt(CREW_COUNT) < 5)
        {
            return ok(views.html.oakisland.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES))
                                          .addingToSession(request, CREW_MEMBERS_SESSION_KEY, CREW_COUNT);
        }
        else
        {
            return ok(views.html.westfromengland.render(CREW_COUNT, KITTEN_IMG, CREW_NAMES))
                                                .addingToSession(request, CREW_MEMBERS_SESSION_KEY, CREW_COUNT);
        }
    }

    public Result postWestEnd(Http.Request request)
    {
        Random r = new Random();
        int RANDOM_NUMBER = (r.nextInt(5) + 1);
        final String PLAYER_NAME = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        final String KITTEN_IMG = request.session().getOptional(KITTEN_IMG_SESSION_KEY).orElse("Unknown");
        final String CREW_COUNT = request.session().getOptional(CREW_MEMBERS_SESSION_KEY).orElse("Unknown");
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));

        if(RANDOM_NUMBER == 1)
        {
            return ok(views.html.dungeon.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES));
        }
        else
        {
            return ok(views.html.westend.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES));
        }
    }

    public Result postNorthFromEngland(Http.Request request)
    {
        final String PLAYER_NAME = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        final String KITTEN_IMG = request.session().getOptional(KITTEN_IMG_SESSION_KEY).orElse("Unknown");
        final String CREW_COUNT = request.session().getOptional(CREW_MEMBERS_SESSION_KEY).orElse("Unknown");
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));

        if (KITTEN_IMG.equals("/assets/images/agro.jpg") && CREW_COUNT.equals("6")) //EasterEgg
        {
            return ok(views.html.vikings.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES));
        }
        else
        {
            return ok(views.html.northfromengland.render(CREW_COUNT, KITTEN_IMG, CREW_NAMES));
        }
    }

    public Result postNorthEnd(Http.Request request)
    {
        Random r = new Random();
        final int RANDOM_NUMBER = (r.nextInt(4) + 1);
        final String PLAYER_NAME = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        final String KITTEN_IMG = request.session().getOptional(KITTEN_IMG_SESSION_KEY).orElse("Unknown");
        final String CREW_COUNT = request.session().getOptional(CREW_MEMBERS_SESSION_KEY).orElse("Unknown");
        final String[] CREW_NAMES = getRemainingCrewMembers(Integer.parseInt(CREW_COUNT));

        if (RANDOM_NUMBER == 1)
        {
            return ok(views.html.northpole.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES));
        }
        else
        {
            return ok(views.html.northend.render(PLAYER_NAME, CREW_COUNT, KITTEN_IMG, CREW_NAMES));
        }
    }

    public String decrementCrewCount(Http.Request request)
    {
        final String CREW_COUNT = request.session().getOptional(CREW_MEMBERS_SESSION_KEY).orElse("Unknown");
        int crewCountToInt = Integer.parseInt(CREW_COUNT);
        crewCountToInt--;
        return String.valueOf(crewCountToInt);
    }

    public String[] getRemainingCrewMembers(int crewCount)
    {
        String[] crewNames = new String[crewCount];

        for (int i = 0; i < crewCount; i++)
        {
            crewNames[i] = CREW_NAMES[i];
        }
        return crewNames;
    }






}
