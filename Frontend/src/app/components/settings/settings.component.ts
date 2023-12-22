import { Component } from '@angular/core';
import { SettingsService } from "../../services/settings.service";
import { UserSettings } from './user-settings.model';

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent {
    settings: UserSettings = {
        mail: '',
        mailserver: '',
        mailpassword: '',
        mailport: 0,
        openaikey: '',
        sendgridkey: ''
    };

    constructor(private settingsService: SettingsService) {}

    submitSettings(): void {
        this.settingsService.submitSettings(this.settings).subscribe(
            response => {
                console.log('Settings updated', response);
            },
            (error: any) => console.error('Error updating settings', error)
        );
    }

}
