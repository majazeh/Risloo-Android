package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.SampleForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SampleModel extends TypeModel {
    private String id = "";
    private String title = "";
    private String edition = "";
    private String filler = "";
    private String scale_id = "";
    private String scale_title = "";
    private String status = "";
    private String description = "";
    private String psychologist_description = "";
    private String primary_term = "";
    private String case_id = "";
    private String case_status = "";
    private String session_id = "";
    private String chain_id = "";
    private int version;
    private int edition_version;
    private int code;
    private int cornometer;
    private int members_count;
    private int joined;
    private int scored_at;
    private int closed_at;
    private int created_at;
    private int started_at;
    private RoomModel room;
    private CaseModel casse;
    private UserModel client;
    private JSONArray terms;
    private List members;
    private List chains;
    private List prerequisites;
    private List items;
    private List entities;
    private List profiles;
    private List profiles_half;
    private List profiles_extra;
    private SampleForm sampleForm;

    public SampleModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("edition"))
                setEdition(jsonObject.getString("edition"));
            if (!jsonObject.isNull("filler"))
                setFiller(jsonObject.getString("filler"));
            if (!jsonObject.isNull("scale")) {
                if (!jsonObject.getJSONObject("scale").isNull("id"))
                    setScaleId(jsonObject.getJSONObject("scale").getString("id"));
                if (!jsonObject.getJSONObject("scale").isNull("title"))
                    setScaleTitle(jsonObject.getJSONObject("scale").getString("title"));
            }
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("description"))
                setDescription(jsonObject.getString("description"));
            if (!jsonObject.isNull("psychologist_description"))
                setPsychologistDescription(jsonObject.getString("psychologist_description"));
            if (!jsonObject.isNull("primary_term"))
                setPrimaryTerm(jsonObject.getString("primary_term"));
            if (!jsonObject.isNull("case_id"))
                setCaseId(jsonObject.getString("case_id"));
            if (!jsonObject.isNull("case_status"))
                setCaseStatus(jsonObject.getString("case_status"));
            if (!jsonObject.isNull("session_id"))
                setSessionId(jsonObject.getString("session_id"));

            if (!jsonObject.isNull("version"))
                setVersion(jsonObject.getInt("version"));
            if (!jsonObject.isNull("edition_version"))
                setEditionVersion(jsonObject.getInt("edition_version"));
            if (!jsonObject.isNull("code"))
                setCode(jsonObject.getInt("code"));
            if (!jsonObject.isNull("cornometer"))
                setCornometer(jsonObject.getInt("cornometer"));
            if (!jsonObject.isNull("members_count"))
                setMembersCount(jsonObject.getInt("members_count"));
            if (!jsonObject.isNull("joined"))
                setJoined(jsonObject.getInt("joined"));
            if (!jsonObject.isNull("scored_at"))
                setScoredAt(jsonObject.getInt("scored_at"));
            if (!jsonObject.isNull("closed_at"))
                setClosedAt(jsonObject.getInt("closed_at"));
            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("started_at"))
                setStartedAt(jsonObject.getInt("started_at"));

            if (!jsonObject.isNull("room"))
                setRoom(new RoomModel(jsonObject.getJSONObject("room")));
            if (!jsonObject.isNull("case"))
                setCasse(new CaseModel(jsonObject.getJSONObject("case")));
            if (!jsonObject.isNull("client"))
                setClient(new UserModel(jsonObject.getJSONObject("client")));

            if (!jsonObject.isNull("terms"))
                setTerms(jsonObject.getJSONArray("terms"));

            if (!jsonObject.isNull("members") && jsonObject.getJSONArray("members").length() != 0) {
                members = new List();

                for (int i = 0; i < jsonObject.getJSONArray("members").length(); i++)
                    members.add(new UserModel(jsonObject.getJSONArray("members").getJSONObject(i)));

                setMembers(members);
            } else {
                setMembers(new List());
            }

            if (!jsonObject.isNull("chain") && jsonObject.getJSONArray("chain").length() != 0) {
                if (jsonObject.get("chain").getClass().getName().equals("org.json.JSONObject")) {
                    JSONObject chain = jsonObject.getJSONObject("chain");

                    if (!chain.isNull("id"))
                        setChainId(chain.getString("id"));

                    if (!chain.isNull("list")) {
                        chains = new List();

                        for (int i = 0; i < chain.getJSONArray("list").length(); i++)
                            chains.add(new ChainModel(chain.getJSONArray("list").getJSONObject(i)));

                        setChains(chains);
                    } else {
                        setChains(new List());
                    }

                } else {
                    setChainId(jsonObject.getString("chain"));
                    setChains(new List());
                }
            } else {
                setChains(new List());
            }

            if (!jsonObject.isNull("prerequisites") && jsonObject.getJSONArray("prerequisites").length() != 0) {
                prerequisites = new List();

                for (int i = 0; i < jsonObject.getJSONArray("prerequisites").length(); i++) {
                    jsonObject.getJSONArray("prerequisites").getJSONObject(i).put("index", i + 1);
                    prerequisites.add(new PrerequisitesModel(jsonObject.getJSONArray("prerequisites").getJSONObject(i)));
                }

                setPrerequisites(prerequisites);
            } else {
                setPrerequisites(new List());
            }


            if (!jsonObject.isNull("items") && jsonObject.getJSONArray("items").length() != 0) {
                items = new List();

                for (int i = 0; i < jsonObject.getJSONArray("items").length(); i++) {
                    jsonObject.getJSONArray("items").getJSONObject(i).put("index", i + 1);
                    items.add(new ItemModel(jsonObject.getJSONArray("items").getJSONObject(i)));
                }

                setItems(items);
            } else {
                setItems(new List());
            }

            if (!jsonObject.isNull("entities") && jsonObject.getJSONArray("entities").length() != 0) {
                entities = new List();

                for (int i = 0; i < jsonObject.getJSONArray("entities").length(); i++) {
                    jsonObject.getJSONArray("entities").getJSONObject(i).put("position", i + 1);
                    entities.add(new EntityModel(jsonObject.getJSONArray("entities").getJSONObject(i)));
                }

                setEntities(entities);
            } else {
                setEntities(new List());
            }

            if (!jsonObject.isNull("profiles") && jsonObject.getJSONArray("profiles").length() != 0) {
                profiles = new List();
                profiles_half = new List();
                profiles_extra = new List();

                for (int i = 0; i < jsonObject.getJSONArray("profiles").length(); i++) {
                    profiles.add(new ProfileModel(jsonObject.getJSONArray("profiles").getJSONObject(i)));

                    if (jsonObject.getJSONArray("profiles").getJSONObject(i).getString("mode").equals("profile_png"))
                        profiles_half.add(new ProfileModel(jsonObject.getJSONArray("profiles").getJSONObject(i)));

                    if (!jsonObject.getJSONArray("profiles").getJSONObject(i).getString("mode").startsWith("profile_png") && jsonObject.getJSONArray("profiles").getJSONObject(i).getString("mode").endsWith("png"))
                        profiles_extra.add(new ProfileModel(jsonObject.getJSONArray("profiles").getJSONObject(i)));

                }

                setProfiles(profiles);
                setProfilesHalf(profiles_half);
                setProfilesExtra(profiles_extra);
            } else {
                setProfiles(new List());
                setProfilesHalf(new List());
                setProfilesExtra(new List());
            }

            sampleForm = new SampleForm(items, psychologist_description, chains, entities, prerequisites, getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getScaleId() {
        return scale_id;
    }

    public void setScaleId(String scale_id) {
        this.scale_id = scale_id;
    }

    public String getScaleTitle() {
        return scale_title;
    }

    public void setScaleTitle(String scale_title) {
        this.scale_title = scale_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPsychologistDescription() {
        return psychologist_description;
    }

    public void setPsychologistDescription(String psychologist_description) {
        this.psychologist_description = psychologist_description;
    }

    public String getPrimaryTerm() {
        return primary_term;
    }

    public void setPrimaryTerm(String primary_term) {
        this.primary_term = primary_term;
    }

    public String getCaseId() {
        return case_id;
    }

    public void setCaseId(String case_id) {
        this.case_id = case_id;
    }

    public String getCaseStatus() {
        return case_status;
    }

    public void setCaseStatus(String case_status) {
        this.case_status = case_status;
    }

    public String getSessionId() {
        return session_id;
    }

    public void setSessionId(String session_id) {
        this.session_id = session_id;
    }

    public String getChainId() {
        return chain_id;
    }

    public void setChainId(String chain_id) {
        this.chain_id = chain_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getEditionVersion() {
        return edition_version;
    }

    public void setEditionVersion(int edition_version) {
        this.edition_version = edition_version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCornometer() {
        return cornometer;
    }

    public void setCornometer(int cornometer) {
        this.cornometer = cornometer;
    }

    public int getMembersCount() {
        return members_count;
    }

    public void setMembersCount(int members_count) {
        this.members_count = members_count;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
    }

    public int getScoredAt() {
        return scored_at;
    }

    public void setScoredAt(int scored_at) {
        this.scored_at = scored_at;
    }

    public int getClosedAt() {
        return closed_at;
    }

    public void setClosedAt(int closed_at) {
        this.closed_at = closed_at;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public int getStartedAt() {
        return started_at;
    }

    public void setStartedAt(int started_at) {
        this.started_at = started_at;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public CaseModel getCasse() {
        return casse;
    }

    public void setCasse(CaseModel casse) {
        this.casse = casse;
    }

    public UserModel getClient() {
        return client;
    }

    public void setClient(UserModel client) {
        this.client = client;
    }

    public JSONArray getTerms() {
        return terms;
    }

    public void setTerms(JSONArray terms) {
        this.terms = terms;
    }

    public List getMembers() {
        return members;
    }

    public void setMembers(List members) {
        this.members = members;
    }

    public List getChains() {
        return chains;
    }

    public void setChains(List chains) {
        this.chains = chains;
    }

    public List getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public List getEntities() {
        return entities;
    }

    public void setEntities(List entities) {
        this.entities = entities;
    }

    public List getProfiles() {
        return profiles;
    }

    public void setProfiles(List profiles) {
        this.profiles = profiles;
    }

    public List getProfilesHalf() {
        return profiles_half;
    }

    public void setProfilesHalf(List profiles_half) {
        this.profiles_half = profiles_half;
    }

    public List getProfilesExtra() {
        return profiles_extra;
    }

    public void setProfilesExtra(List profiles_extra) {
        this.profiles_extra = profiles_extra;
    }

    public SampleForm getSampleForm() {
        return sampleForm;
    }

    public void setSampleForm(SampleForm sampleForm) {
        this.sampleForm = sampleForm;
    }

    public boolean compareTo(SampleModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!edition.equals(model.getEdition()))
                return false;

            if (!filler.equals(model.getFiller()))
                return false;

            if (!scale_id.equals(model.getScaleId()))
                return false;

            if (!scale_title.equals(model.getScaleTitle()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!description.equals(model.getDescription()))
                return false;

            if (!psychologist_description.equals(model.getPsychologistDescription()))
                return false;

            if (!primary_term.equals(model.getPrimaryTerm()))
                return false;

            if (!case_id.equals(model.getCaseId()))
                return false;

            if (!case_status.equals(model.getCaseStatus()))
                return false;

            if (!session_id.equals(model.getSessionId()))
                return false;

            if (!chain_id.equals(model.getChainId()))
                return false;

            if (version != model.getVersion())
                return false;

            if (edition_version != model.getEditionVersion())
                return false;

            if (code != model.getCode())
                return false;

            if (cornometer != model.getCornometer())
                return false;

            if (members_count != model.getMembersCount())
                return false;

            if (joined != model.getJoined())
                return false;

            if (scored_at != model.getScoredAt())
                return false;

            if (closed_at != model.getClosedAt())
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (started_at != model.getStartedAt())
                return false;

            if (room != model.getRoom())
                return false;

            if (casse != model.getCasse())
                return false;

            if (client != model.getClient())
                return false;

            if (terms != model.getTerms())
                return false;

            if (members != model.getMembers())
                return false;

            if (chains != model.getChains())
                return false;

            if (prerequisites != model.getPrerequisites())
                return false;

            if (items != model.getItems())
                return false;

            if (entities != model.getEntities())
                return false;

            if (profiles != model.getProfiles())
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("id", getId());
            super.toObject().put("title", getTitle());
            super.toObject().put("edition", getEdition());
            super.toObject().put("filler", getFiller());
            super.toObject().put("scale_id", getScaleId());
            super.toObject().put("scale_title", getScaleTitle());
            super.toObject().put("status", getStatus());
            super.toObject().put("description", getDescription());
            super.toObject().put("psychologist_description", getPsychologistDescription());
            super.toObject().put("primary_term", getPrimaryTerm());
            super.toObject().put("case_id", getCaseId());
            super.toObject().put("case_status", getCaseStatus());
            super.toObject().put("session_id", getStatus());
            super.toObject().put("chain_id", getChainId());
            super.toObject().put("version", getVersion());
            super.toObject().put("edition_version", getEditionVersion());
            super.toObject().put("code", getCode());
            super.toObject().put("cornometer", getCornometer());
            super.toObject().put("members_count", getMembersCount());
            super.toObject().put("joined", getJoined());
            super.toObject().put("scored_at", getScoredAt());
            super.toObject().put("closed_at", getClosedAt());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("started_at", getStartedAt());
            super.toObject().put("room", getRoom().toObject());
            super.toObject().put("casse", getCasse().toObject());
            super.toObject().put("client", getClient().toObject());
            super.toObject().put("terms", getTerms());
            super.toObject().put("members", getMembers());
            super.toObject().put("chains", getChains());
            super.toObject().put("prerequisites", getPrerequisites());
            super.toObject().put("items", getItems());
            super.toObject().put("entities", getEntities());
            super.toObject().put("profiles", getProfiles());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "SampleModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", edition='" + edition + '\'' +
                ", filler='" + filler + '\'' +
                ", scale_id='" + scale_id + '\'' +
                ", scale_title='" + scale_title + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", psychologist_description='" + psychologist_description + '\'' +
                ", primary_term='" + primary_term + '\'' +
                ", case_id='" + case_id + '\'' +
                ", case_status='" + case_status + '\'' +
                ", session_id='" + session_id + '\'' +
                ", chain_id='" + chain_id + '\'' +
                ", version=" + version +
                ", edition_version=" + edition_version +
                ", code=" + code +
                ", cornometer=" + cornometer +
                ", members_count=" + members_count +
                ", joined=" + joined +
                ", scored_at=" + scored_at +
                ", closed_at=" + closed_at +
                ", created_at=" + created_at +
                ", started_at=" + started_at +
                ", room=" + room +
                ", casse=" + casse +
                ", client=" + client +
                ", terms=" + terms +
                ", members=" + members +
                ", chains=" + chains +
                ", prerequisites=" + prerequisites +
                ", items=" + items +
                ", entities=" + entities +
                ", profiles=" + profiles +
                '}';
    }

}