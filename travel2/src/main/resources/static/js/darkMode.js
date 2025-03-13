document.addEventListener('DOMContentLoaded', () => {
    const themeSelect = document.getElementById('theme-select');
    const heading = document.querySelector('h1'); // Select the h1

    // Check for saved theme preference
    const savedTheme = localStorage.getItem('theme') || 'light';
    document.body.classList.toggle('dark-mode', savedTheme === 'dark');
    themeSelect.value = savedTheme;

    // Ensure h1 is always visible
    heading.style.setProperty('color', savedTheme === 'dark' ? '#EEEEEE' : '#222831', 'important');

    // Listen for theme changes
    themeSelect.addEventListener('change', () => {
        const selectedTheme = themeSelect.value;
        document.body.classList.toggle('dark-mode', selectedTheme === 'dark');
        heading.style.setProperty('color', selectedTheme === 'dark' ? '#EEEEEE' : '#222831', 'important');
        localStorage.setItem('theme', selectedTheme);
    });
});
