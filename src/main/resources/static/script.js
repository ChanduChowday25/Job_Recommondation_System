const BASE_URL = "http://localhost:8081";

// 🔁 Toggle UI
function showRegister() {
    loginForm.style.display = "none";
    registerForm.style.display = "block";
}

function showLogin() {
    loginForm.style.display = "block";
    registerForm.style.display = "none";
}

// 🆕 REGISTER
function register() {
    fetch(BASE_URL + "/auth/register", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            name: rname.value,
            email: remail.value,
            password: rpassword.value,
            role: "CANDIDATE"
        })
    })
    .then(res => res.text())
    .then(data => {
        alert(data);
        showLogin();
    });
}

// 🔐 LOGIN
function login() {
    fetch(BASE_URL + "/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            email: email.value,
            password: password.value
        })
    })
    .then(res => res.text())
    .then(data => {
        alert(data);

        if (data === "LOGIN SUCCESS") {
            localStorage.setItem("login", "true");
            authBox.style.display = "none";
            dashboard.style.display = "block";
            loadData();
        }
    });
}

// 🚪 LOGOUT
function logout() {
    localStorage.removeItem("login");
    location.reload();
}

// 📥 LOAD DATA
function loadData() {

    fetch(BASE_URL + "/candidates")
    .then(res => res.json())
    .then(data => {
        candidateSelect.innerHTML = "";
        recommendCandidate.innerHTML = "";

        data.forEach(c => {
            candidateSelect.innerHTML += `<option value="${c.id}">${c.name}</option>`;
            recommendCandidate.innerHTML += `<option value="${c.id}">${c.name}</option>`;
        });
    });

    fetch(BASE_URL + "/skills")
    .then(res => res.json())
    .then(data => {
        skillSelect.innerHTML = "";
        skillSelectJob.innerHTML = "";

        data.forEach(s => {
            skillSelect.innerHTML += `<option value="${s.id}">${s.name}</option>`;
            skillSelectJob.innerHTML += `<option value="${s.id}">${s.name}</option>`;
        });
    });

    fetch(BASE_URL + "/jobs")
    .then(res => res.json())
    .then(data => {
        jobSelect.innerHTML = "";

        data.forEach(job => {
            jobSelect.innerHTML += `<option value="${job.id}">${job.title}</option>`;
        });
    });
}

// ➕ ADD CANDIDATE
function addCandidate() {
    fetch(BASE_URL + "/candidates", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            name: cname.value,
            email: cemail.value,
            experience: parseInt(cexp.value)
        })
    })
    .then(res => res.json())
    .then(() => {
        alert("Candidate Added");
        loadData();
    });
}

// ➕ ADD JOB
function addJob() {
    fetch(BASE_URL + "/jobs", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            title: jtitle.value,
            description: jdesc.value,
            experienceRequired: parseInt(jexp.value),
            location: jloc.value
        })
    })
    .then(res => res.json())
    .then(() => {
        alert("Job Added");
        loadData();
    });
}

// ➕ ADD SKILL
function addSkill() {
    fetch(BASE_URL + "/skills", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            name: skill.value
        })
    })
    .then(res => res.json())
    .then(() => {
        alert("Skill Added");
        loadData();
    });
}

// 🔗 ASSIGN
function assignCandidateSkill() {
    fetch(`${BASE_URL}/candidate-skill?candidateId=${candidateSelect.value}&skillId=${skillSelect.value}`, {
        method: "POST"
    })
    .then(() => alert("Assigned to Candidate"));
}

function assignJobSkill() {
    fetch(`${BASE_URL}/job-skill/assign?jobId=${jobSelect.value}&skillId=${skillSelectJob.value}`, {
        method: "POST"
    })
    .then(() => alert("Assigned to Job"));
}

// 🔍 RECOMMEND
function getJobs() {
    fetch(BASE_URL + "/recommend/" + recommendCandidate.value)
    .then(res => res.json())
    .then(data => {
        result.innerHTML = "";

        data.forEach(job => {
            result.innerHTML += `
                <li>
                    <b>${job.title}</b><br>
                    Match: ${job.matchPercentage.toFixed(2)}%
                </li>`;
        });
    });
}

// 🔁 AUTO LOGIN
window.onload = function() {
    if (localStorage.getItem("login") === "true") {
        authBox.style.display = "none";
        dashboard.style.display = "block";
        loadData();
    }
};