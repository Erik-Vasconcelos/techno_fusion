function updateCountdown() {
    // Obtém a data e hora atual
    const now = new Date();

    // Calcula os dias restantes até o próximo sábado
    const daysUntilSaturday = 6 - now.getDay() + (now.getDay() >= 6 ? 7 : 0);

    // Define a data e hora do próximo sábado à meia-noite
    const nextSaturday = new Date(now);
    nextSaturday.setDate(now.getDate() + daysUntilSaturday);
    nextSaturday.setHours(0, 0, 0, 0);

    // Calcula o tempo restante em milissegundos
    const timeRemaining = nextSaturday - now;

    // Calcula dias, horas, minutos e segundos
    const days = Math.floor(timeRemaining / (1000 * 60 * 60 * 24));
    const hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);

    // Atualiza os elementos HTML correspondentes
    document.getElementById('days').innerText = days;
    document.getElementById('hours').innerText = hours;
    document.getElementById('minutes').innerText = minutes;
    document.getElementById('seconds').innerText = seconds;
}

// Chama a função para atualizar a contagem imediatamente
updateCountdown();

// Chama a função para atualizar a contagem a cada segundo
setInterval(updateCountdown, 1000);
